/** @type {String[]} */
const data = (await (Bun.file(Bun.argv[2])).text()).split("\n");

function part_one() {
  const joltages = [];
  for (let i = 0; i < data.length; i++) {
    const line = data[i];
    const max = [0, 0]; // 0 => 1st switch, 1 => 2nd switch, 2 => 1st index
    let max_idx = 0;
    for (let j = 0; j < line.length - 2; j++) {
      let n = parseInt(line[j]);
      if (n > max[0]) {
        max[0] = n;
        max_idx = j;
      }
    }
    for (let j = max_idx + 1; j < line.length; j++) {
      let n = parseInt(line[j]);
      if (n > max[1]) max[1] = n;
    }
    let joltage = 10 * max[0] + max[1];
    joltages.push(joltage);
  }

  let s = 0
  for (const j of joltages) {
    s += j;
  }
  console.log(s);
}
part_one();

const MAX_SWITCHES = 12;
function part_two() {
  const joltages = [];
  for (let i = 0; i < data.length; i++) {
    const line = data[i].trim();
    const max = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    let max_idx = 0;

    for (let j = 0; j < MAX_SWITCHES; j++) {
      for (let k = max_idx; k <= line.length - (MAX_SWITCHES - j); k++) {
        let n = parseInt(line[k]);
        if (n > max[j]) {
          max[j] = n;
          max_idx = k + 1;
        }
      }
    }
    let joltage = 0;
    for (let j = 0; j < MAX_SWITCHES; j++) joltage += max[j] * 10 ** (MAX_SWITCHES - 1 - j);
    joltages.push(joltage);
  }

  let s = 0
  for (const j of joltages) s += j;
  console.log(s);
}
part_two();