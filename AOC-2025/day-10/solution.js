import solver from "javascript-lp-solver"; // https://github.com/JWally/jsLPSolver

/** @type {string[]} */
const data = (await (Bun.file(Bun.argv[2])).text()).split("\n");

/**
 * @param {string} goalState 
 * @param {string[]} startState 
 * @param {number[][]} buttons 
 */
function findMinPresses(goalState, startState, buttons) {
  const queue = [];
  const visited = new Set();
  queue.push({ state: startState, presses: 0 });
  visited.add(startState.join(""));

  while (queue.length > 0) {
    const { state, presses } = queue.shift();
    if (state.join("") == goalState) return presses;
    
    for (const button of buttons) {
      const newState = state.slice();
      for (const i of button) newState[i] = newState[i] == "." ? "#" : ".";
      const strState = newState.join("");
      if (!visited.has(strState)) {
        visited.add(strState);
        queue.push({ state: newState, presses: presses + 1});
      }
    }
  }
}

function part_one() {
  let presses = 0;
  for (const line of data) {
    const goalState = line.substring(1, line.lastIndexOf("]"));
    /** @type {string[]} */
    const startState = new Array(goalState.length).fill(".");
    /** @type {number[][]} */
    const buttons = line.substring(line.indexOf("("), line.lastIndexOf(")") + 1)
      .split(" ")
      .map(
        arr => arr.substring(1, arr.length - 1)
          .split(",")
          .map(v => parseInt(v))
      );
    presses += findMinPresses(goalState, startState, buttons);
  }
  console.log(presses);
}
part_one();


function part_two() {
  let presses = 0;
  for (const line of data) {
    const target = line.substring(line.indexOf("{") + 1, line.lastIndexOf("}")).split(",");
    /** @type {number[][]} */
    const buttons = line.substring(line.indexOf("("), line.lastIndexOf(")") + 1)
      .split(" ")
      .map(
        arr => arr.substring(1, arr.length - 1)
          .split(",")
          .map(v => parseInt(v))
      );
    const model = {
      optimize: "presses",
      opType: "min",
      constraints: Object.fromEntries(
        target.map((v, i) => ["c" + i, { equal: parseInt(v)}])
      ),
      variables: Object.fromEntries(
        buttons.map((btn, j) => [
          "x"+j,
          btn.reduce(
            (acc, i) => (acc["c"+i] = 1, acc),
            { presses: 1 }
          )
        ])
      ),
      ints: Object.fromEntries(buttons.map((_, i) => ["x" + i, i]))
    };

    const result = solver.Solve(model);
    presses += result.result;
  }
  console.log(presses);
}
part_two();