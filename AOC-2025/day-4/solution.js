/** @type {String[]} */
let data = (await (Bun.file(Bun.argv[2])).text()).split("\n").map(r => r.trim().split(""));

const offsets = [
  {x: 0, y: -1}, // up
  {x: 0, y: 1},  // down
  {x: -1, y: 0}, // left
  {x: 1, y: 0},  // right
  {x: 1, y: -1}, // top-right
  {x: -1, y: -1},// top-left
  {x: 1, y: 1},  // bottom-right
  {x: -1, y: 1}  // bottom-left
];

const ROWS = data.length;
const COLS = data[0].length;

function in_bounds(x, y, off) {
  return !(y + off.y >= ROWS || y + off.y < 0 || x + off.x >= COLS || x + off.x < 0);
}

function part_one() {
  let available = 0;
  for (let y = 0; y < ROWS; y++) {
    for (let x = 0; x < COLS; x++) {
      if (data[y][x] != "@") continue;
      let roll_count = 0;
      for (const off of offsets) {
        if (!in_bounds(x, y, off)) continue;
        if (data[y + off.y][x + off.x] == "@") roll_count++;
      }
      if (roll_count < 4) available++;
    }
  }
  return available;
}
console.log(part_one());

function part_two() {
  let removed = 0;

  while (part_one() > 0) {
    for (let y = 0; y < ROWS; y++) {
      for (let x = 0; x < COLS; x++) {
        if (data[y][x] != "@") continue;
        let roll_count = 0;
        for (const off of offsets) {
          if (!in_bounds(x, y, off)) continue;
          if (data[y + off.y][x + off.x] == "@") roll_count++;
        }
        if (roll_count < 4) {
          data[y][x] = ".";
          removed++;
        }
      }
    }
  }
  console.log(removed);
}
part_two();