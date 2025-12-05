/** @type {String[]} */
let data = (await (Bun.file(Bun.argv[2])).text()).split("\n");

function part_one() {
  /** @type Number[] */
  const freshIngredients = [];
  let availableIngredients = false;
  let count = 0;

  for (let line of data) {
    line = line.trim();
    if (line == "") availableIngredients = true;

    if (!availableIngredients) {
      const split = line.split("-");
      freshIngredients.push(parseInt(split[0]), parseInt(split[1]));
    } else {
      const ingredientID = parseInt(line);
      for (let i = 0; i < freshIngredients.length; i += 2) {
        if (ingredientID >= freshIngredients[i] && ingredientID <= freshIngredients[i + 1]) {
          count++;
          break;
        }
      }
    }
  }
  console.log(count);
}
part_one();

function part_two() {
  /** @type number[] */
  const freshIngredients = [];
  for (let line of data) {
    line = line.trim();
    if (line == "") break;
    const split = line.split("-");
    freshIngredients.push([parseInt(split[0]), parseInt(split[1])]);
  }

  freshIngredients.sort((a, b) => a[0] - b[0]);
  const merged = [];
  let curRange = freshIngredients[0];
  for (let i = 1; i < freshIngredients.length; i++) {
    const nextRange = freshIngredients[i];
    
    if (curRange[1] >= nextRange[0]) {
      curRange[1] = Math.max(curRange[1], nextRange[1]);
    } else {
      merged.push(curRange);
      curRange = nextRange;
    }
  }
  merged.push(curRange);
  
  let fresh = 0;
  for (let i = 0; i < merged.length; i++) fresh += 1 + (merged[i][1] - merged[i][0]);
  console.log(fresh);
}
part_two();