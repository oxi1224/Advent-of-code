/** @type {string[]} */
const data = (await (Bun.file(Bun.argv[2])).text()).split("\n");

/** @type {Map<number, [number, string[]]} */
const presents = new Map();
/**
 * @typedef {Object} area
 * @property {number} width
 * @property {number} height
 * @property {number[]} quantities
 */
/** @type {area[]} */
const areas = []

let presentIdx = 0;
let curPresent = [0, []];
for (let i = 0; i < data.length; i++) {
  const line = data[i].trim();
  if (line == "") continue;
  if (line.includes(":") && line.includes("x")) {
    const dimensions = line.slice(0, line.indexOf(":")).split("x");
    const quantities = line.slice(line.indexOf(" ") + 1).split(" ").map(s => parseInt(s));
    areas.push({
      width: parseInt(dimensions[0]),
      height: parseInt(dimensions[1]),
      quantities: quantities
    });
  } else if (line.includes(":")) {
    const idx = parseInt(line.slice(0, line.length - 1));
    const shape = [data[i + 1].trim(), data[i + 2].trim(), data[i + 3].trim()];
    i += 3;
    let area = 0;
    for (const l of shape) area += l.replaceAll(".", "").length;
    presents.set(idx, [area, shape]);
  }
}

function part_one() {
  let willFit = 0;
  for (const area of areas) {
    let totalAreaNeeded = 0;
    for (let i = 0; i < area.quantities.length; i++) {
      totalAreaNeeded += (presents.get(i))[0] * area.quantities[i];
    }
    if (totalAreaNeeded < area.width * area.height) willFit += 1;
  }
  console.log(willFit);
}
part_one();