/** @type {string[]} */
const data = (await (Bun.file(Bun.argv[2])).text()).split("\n");

/** @type {Map<String, String[]} */
const connectionMap = new Map();
for (const line of data) {
  const key = line.substring(0, line.indexOf(":")).trim();
  const connections = line.substring(line.indexOf(" ")).trim().split(" ");
  connectionMap.set(key, connections);
}

function part_one() {
  const queue = ["you"];
  let paths = 0;
  while (queue.length > 0) {
    const key = queue.shift();
    if (key == "out") {
      paths++;
      continue;
    }

    for (const connection of connectionMap.get(key)) queue.push(connection);
  }
  console.log(paths);
}
part_one();

function part_two() {
  const memo = new Map();
  
  function countPaths(current, target, visited, hasFft, hasDac) {
    if (current === target) return hasFft && hasDac ? 1 : 0;
    const lastNodes = Array.from(visited).slice(-3).join(',');
    const memoKey = `${current}|${hasFft}|${hasDac}|${lastNodes}`;
    
    if (memo.has(memoKey)) return memo.get(memoKey);
    visited.add(current);
    let total = 0;
    
    for (const node of connectionMap.get(current)) {
      if (visited.has(node)) continue;
      total += countPaths(
        node,
        target,
        new Set(visited),
        hasFft || node == "fft",
        hasDac || node == "dac"
      );
    }
    memo.set(memoKey, total);
    return total;
  }
  const result = countPaths("svr", "out", new Set(["svr"]), false, false);
  console.log(result);
}
part_two();