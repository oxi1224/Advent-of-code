#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

int is_safe(int* report, int len) {
  if (len < 2) return 1; // With error correction, a report with length 2 is always valid;
  int is_rising = report[1] > report[0];
  for (int i = 1; i < len; i++) {
    int diff = abs(report[i] - report[i - 1]);
    if (
      diff > 3 || diff == 0 ||
      (is_rising && report[i] < report[i - 1]) ||
      (!is_rising && report[i] > report[i - 1])
    ) return 0;
  }
  return 1;
}

int main() {
  FILE* f;
  f = fopen("input.txt", "r");
  char report_line[256];

  int safe_reports = 0;
  while (fgets(report_line, 256, f) != NULL) {
    char* token = strtok(report_line, " ");
    int* report = malloc(sizeof(int));
    report[0] = atoi(token);
    int count = 1;

    while ((token = strtok(NULL, " ")) != NULL) {
      report = realloc(report, sizeof(int) * (count + 1));
      report[count] = atoi(token);
      count++;
    }

    if (is_safe(report, count)) {
      safe_reports++;
      continue;
    }

    int corrected_safe = 0;
    for (int i = 0; i < count; i++) {
      int temp[count];
      int temp_count = 0;

      for (int j = 0; j < count; j++) {
        if (j != i) {
          temp[temp_count] = report[j];
          temp_count++;
        }
      }

      if (is_safe(temp, temp_count)) {
        corrected_safe = 1;
        break;
      }
    }
    if (corrected_safe) safe_reports++;
  }

  printf("%d\n", safe_reports);
  return 0;
}

