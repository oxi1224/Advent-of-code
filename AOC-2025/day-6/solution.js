/** @type {String[]} */
const data = (await (Bun.file(Bun.argv[2])).text()).split("\n");

const ROWS = data.length;
const COLS = data[0].length;
function part_one() {
  let sum = 0;
  const rows = []
  for (let i = 0; i < ROWS; i++) {
    rows.push(data[i].split(" ").filter(v => v.trim() != ""));
  }
  for (let x = 0; x < rows[0].length; x++) {
    const operands = [];
    for (let y = 0; y < rows.length; y++) {
      const value = rows[y][x].trim();
      if (value == "*") sum += operands.reduce((p, c) => p * c, 1);
      if (value == "+") sum += operands.reduce((p, c) => p + c, 0);
      operands.push(parseInt(value));
    }
  }
  console.log(sum);
}
part_one();

function part_two() {
  let sum = 0;

  /** @type {String[][]} */
  const operations = [];
  let curData = new Array(ROWS - 1).fill("");
  for (let x = 0; x < COLS; x++) {
    const col = data.map(r => r[x]);
    if (!col.every((v) => v == " ")) {
      for (let i = 0; i < ROWS - 1; i++) curData[i] += col[i];
      if (curData.length != ROWS) curData.push(col.at(-1));
    }
    if (col.every((v) => v == " ") || x == COLS - 1) {
      operations.push(curData.map(v => v.replace("\r", "")));
      curData = new Array(ROWS - 1).fill("");
      continue;
    }
  }

  for (const opData of operations) {
    const op = opData.at(-1);
    let result = op == "*" ? 1 : 0;
    for (let x = 0; x < opData[0].length; x++) {
      const num = parseInt(opData.map(r => r[x]).join(""));
      if (op == "*") result *= num;
      else result += num;
    }
    sum += result;
  }
  console.log(sum);
}
part_two();