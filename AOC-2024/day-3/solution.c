#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int is_num(char c) {
  if (c >= '0' && c <= '9') return 1;
  return 0;
}

int main() {
  FILE* f;
  f = fopen("input.txt", "r");

  int total = 0;
  char l[16] = {0};
  char r[16] = {0};
  char saved[5] = {0};
  int num_count = 0;
  int saved_count = 0;
  int reading_left = 1;

  char c;
  while((c = fgetc(f)) != EOF) {
    if (saved_count < 4) {
      if (c == "mul("[saved_count]) {
        saved[saved_count] = c;
        saved_count++;
        if (saved_count == 4) {
          reading_left = 1;
          num_count = 0;
          memset(l, 0, sizeof(l));
          memset(r, 0, sizeof(r));
        }
      } else {
        saved_count = 0;
        memset(saved, 0, sizeof(saved));
      }
      continue;
    }

    if (is_num(c)) {
      if (reading_left) l[num_count] = c;
      else r[num_count] = c;
      num_count++;
      continue;
    }

    if (c == ',' && saved_count == 4 && reading_left) {
      reading_left = 0;
      num_count = 0;
      continue;
    }

    if (c == ')' && saved_count == 4 && !reading_left) {
      total += atoi(l) * atoi(r);
      saved_count = 0;
      memset(saved, 0, sizeof(saved));
      reading_left = 1;
      num_count = 0;
      continue;
    }

    saved_count = 0;
    memset(saved, 0, sizeof(saved));
  }
  
  printf("%d\n", total);
}
