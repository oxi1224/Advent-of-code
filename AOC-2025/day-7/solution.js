/** @type {String[]} */
const data = (await (Bun.file(Bun.argv[2])).text()).split("\n").map(l => l.trim().split(""));

let part_one_count = 0;
/** @type Set<string> */
const visited = new Set();
function part_one(x, y) {
  if (y == data.length || visited.has(y + "-" + x)) return;
  if (data[y][x] == "^") {
    part_one_count++;
    visited.add(y + "-" + x);
    part_one(x - 1, y);
    part_one(x + 1, y);
  } else {
    part_one(x, y + 1);
  }
}
part_one(Math.floor(data[0].length / 2), 0);
console.log(part_one_count);


/** @type Map<String, number> */
const memo = new Map();
function part_two(y, x) {
  if (y >= data.length || x < 0 || x >= data[0].length) return 0;
  if (y == data.length - 1) return 1;

  const key = y + "," + x;
  if (memo.has(key)) return memo.get(key);

  let res = 0;
  if (data[y][x] == "^") res = part_two(y + 1, x - 1) + part_two(y + 1, x + 1);
  else res = part_two(y + 1, x);

  memo.set(key, res);
  return res;
}
console.log(part_two(0, Math.floor(data[0].length / 2)));