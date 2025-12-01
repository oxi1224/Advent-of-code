const data = (await (Bun.file(Bun.argv[2])).text()).split("\n");

function part_one() {
	let pos = 50;
	let count = 0;
  for (const e of data) {
    pos += parseInt(e.substring(1)) * (e.charAt(0) == "L" ? -1 : 1);
    pos = pos < 0 ? (100 + pos) % 100 : pos % 100;
    if (pos == 0) count++;
  }
	console.log(count);
}
part_one();

function part_two() {
	let pos = 50;
	let count = 0;

  for (const e of data) {
    let shift = parseInt(e.substring(1)) * (e.charAt(0) == "L" ? -1 : 1);
    const rots = Math.floor(Math.abs(shift) / 100);
    count += rots;
    shift = shift > 0 ? shift - 100 * rots : shift + 100 * rots;
    pos += shift
    if ((pos > 100 && pos - shift < 100) || (pos < 0 && pos - shift > 0 )) count++;
    pos = pos < 0 ? (100 + pos) % 100 : (pos) % 100;
    if (pos == 0) count++;
  }
	console.log(count)
}
part_two();