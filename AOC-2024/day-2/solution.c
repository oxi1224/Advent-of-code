#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

int main() {
  FILE* f;
  f = fopen("input.txt", "r");
  char report[256];

  int safe_reports = 0;
  while (fgets(report, 256, f) != NULL) {
    char* token = strtok(report, " ");
    if (token == NULL) break;

    int previous_level = atoi(token);
    int is_rising = -1; // -1 = unknown, 0 = decreasing, 1 = rising
    int is_safe = 1;

    while ((token = strtok(NULL, " ")) != NULL) {
      int level = atoi(token);
      int diff = abs(previous_level - level);
      if (is_rising == -1) is_rising = level > previous_level;
      if (
        (diff > 3 || diff == 0) ||
        (is_rising && previous_level > level) ||
        (!is_rising && previous_level < level)
      ) {
        is_safe = 0;
        break;
      } 
      previous_level = level;
    }

    if (is_safe) safe_reports++;
  }

  printf("%d\n", safe_reports);

  return 0;
}

