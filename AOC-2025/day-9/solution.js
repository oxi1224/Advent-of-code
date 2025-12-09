/** @type {[number, number][]} */
const data = (await (Bun.file(Bun.argv[2])).text()).split("\n").map(v => v.trim().split(",").map(v => parseInt(v)));

function inRect(p1, p2, check) {
  return (
    check[0] >= p1[0] && check[0] <= p2[0] &&
    check[1] >= p1[1] && check[1] <= p2[1]
  );
}

function part_one() {
  let max_area = 0;
  for (let i = 0; i < data.length; i++) {
    const p1 = data[i];
    for (let j = i + 1; j < data.length; j++) {
      const p2 = data[j]
      let containsRedTile = false;      
      for (let k = j + 1; k < data.length; k++) {
        if (!inRect(p1, p2, data[k], false)) continue;
        containsRedTile = true;
        break;
      }
      const area = (Math.abs(p1[0] - p2[0]) + 1) * (Math.abs(p1[1] - p2[1]) + 1);
      if (!containsRedTile && area > max_area) max_area = area;
    }
  }
  console.log(max_area);
}
part_one();

function normalizeCoords(p1, p2) {
  return {
    minX: Math.min(p1[0], p2[0]),
    minY: Math.min(p1[1], p2[1]),
    maxX: Math.max(p1[0], p2[0]),
    maxY: Math.max(p1[1], p2[1])
  };
}

/** @type [[number, number], [number, number]][] */
const edges = [];
for (let i = 0; i < data.length - 1; i++) edges.push([data[i], data[i + 1]]);
edges.push([data[data.length - 1], data[0]]);

function rectInBounds(p1, p2) {
  const rect = normalizeCoords(p1, p2);
  for (const [e1, e2] of edges) {
    const edgeRect = normalizeCoords(e1, e2);
    if (
      rect.minX < edgeRect.maxX && rect.maxX > edgeRect.minX &&
      rect.minY < edgeRect.maxY && rect.maxY > edgeRect.minY
    ) return false;
  }
  return true;
}

function part_two() {
  let max_area = 0;
  for (let i = 0; i < data.length; i++) {
    const p1 = data[i];
    for (let j = i + 1; j < data.length; j++) {
      const p2 = data[j]
      const area = (Math.abs(p1[0] - p2[0]) + 1) * (Math.abs(p1[1] - p2[1]) + 1);
      if (area > max_area && rectInBounds(p1, p2) && area > max_area) max_area = area;
    }
  }
  console.log(max_area);
}
part_two();