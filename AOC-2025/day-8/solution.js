/** @type {Vec3[]} */
const data = (await (Bun.file(Bun.argv[2])).text()).split("\n");

function parseCoords(l) {
  const s = l.trim().split(",");
  return [parseInt(s[0]), parseInt(s[1]), parseInt(s[2])];
}

const MAX_CONNECTIONS = parseInt(Bun.argv[3]) || 1_000;
function part_one() {
  /** @type {Map<String, number>} */
  const distances = new Map();
  for (let i = 0; i < data.length; i++) {
    const A = parseCoords(data[i]);
    for (let j = i + 1; j < data.length; j++) {
      const B = parseCoords(data[j]) ;
      const key = `${A[0]},${A[1]},${A[2]}_${B[0]},${B[1]},${B[2]}`;
      distances.set(key, (A[0] - B[0])**2 + (A[1] - B[1])**2 + (A[2] - B[2])**2);
    }
  }

  /** @type [string, number][] */
  const sortedDistances = [...distances.entries()].sort((a, b) => a[1] - b[1]);
  /** @type String[][] */
  const circuits = [];

  for (let i = 0; i < MAX_CONNECTIONS; i++) {
    const points = sortedDistances[i][0].split("_");
    const pointA = points[0];
    const pointB = points[1];

    const idxA = circuits.findIndex(s => s.includes(pointA));
    const idxB = circuits.findIndex(s => s.includes(pointB));
    if (idxA != -1 && idxB != -1 && idxA == idxB) continue;

    if (idxA == -1 && idxB == -1) circuits.push([pointA, pointB]);
    else if (idxA != -1 && idxB == -1) circuits[idxA].push(pointB);
    else if (idxA == -1 && idxB != -1) circuits[idxB].push(pointA);
    else if (idxA != -1 && idxB != -1) {
      circuits[idxA] = [...circuits[idxA], ...circuits[idxB]];
      circuits.splice(idxB, 1);
    }
  }
  const lengths = circuits.map(c => c.length).sort((a, b) => b - a);
  console.log(lengths[0] * lengths[1] * lengths[2]);
}
part_one();

function part_two() {
  /** @type {Map<String, number>} */
  const distances = new Map();
  for (let i = 0; i < data.length; i++) {
    const A = parseCoords(data[i]);
    for (let j = i + 1; j < data.length; j++) {
      const B = parseCoords(data[j]) ;
      const key = `${A[0]},${A[1]},${A[2]}_${B[0]},${B[1]},${B[2]}`;
      distances.set(key, (A[0] - B[0])**2 + (A[1] - B[1])**2 + (A[2] - B[2])**2);
    }
  }

  /** @type [string, number][] */
  const sortedDistances = [...distances.entries()].sort((a, b) => a[1] - b[1]);
  /** @type String[][] */
  const circuits = [];
  let product = 0;
  for (let i = 0; i < sortedDistances.length; i++) {
    const points = sortedDistances[i][0].split("_");
    const pointA = points[0];
    const pointB = points[1];

    const idxA = circuits.findIndex(s => s.includes(pointA));
    const idxB = circuits.findIndex(s => s.includes(pointB));
    if (idxA != -1 && idxB != -1 && idxA == idxB) continue;

    if (idxA == -1 && idxB == -1) circuits.push([pointA, pointB]);
    else if (idxA != -1 && idxB == -1) circuits[idxA].push(pointB);
    else if (idxA == -1 && idxB != -1) circuits[idxB].push(pointA);
    else if (idxA != -1 && idxB != -1) {
      circuits[idxA] = [...circuits[idxA], ...circuits[idxB]];
      circuits.splice(idxB, 1);
    }
    product = parseInt(pointA.split(",")[0]) * parseInt(pointB.split(",")[0]);
  }
  console.log(product);
}
part_two();