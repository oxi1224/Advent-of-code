/** @type {[number, number][]} */
let data = (await (Bun.file(Bun.argv[2])).text()).split(",").map(s => {
  const split = s.split("-");
  return [parseInt(split[0]), parseInt(split[1])];
});

function part_one() {
  let sum = 0;
  for (const range of data) {
    for (let num = range[0]; num <= range[1]; num++) {
      const id = num.toString();
      if (id.length % 2 != 0) continue;
      const middle = id.length / 2;
      if (id.substring(0, middle) == id.substring(middle)) sum += num;
    }
  }
  console.log(sum);
}
part_one();

function part_two() {
  let sum = 0;
  for (const range of data) {
    for (let num = range[0]; num <= range[1]; num++) {
      const id = num.toString();
      let delim = id[0];

      for (let i = 1; i <= Math.floor(id.length / 2); i++) {
        if (!id.split(delim).every(v => v == "")) {
          delim += id[i];
          continue;
        }
        sum += num;
        break; 
      }
    }
  }
  console.log(sum);
}
part_two();