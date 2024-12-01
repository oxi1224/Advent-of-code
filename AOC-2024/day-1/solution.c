#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

int* remove_index(int* arr, int i, int len) {
  for (int j = i; j < len - 1; j++) {
    arr[j] = arr[j + 1];
  }
  return realloc(arr, sizeof(int) * (len - 1));
}

int main() {
  FILE* f;
  f = fopen("input.txt", "r");
  char line[256];
  int* left = NULL;
  int* right = NULL;
  
  int lines = 0;
  while (fgets(line, 256, f) != NULL) {
    char* l = strtok(line, "   ");
    char* r = strtok(NULL, "   ");

    size_t new_size = sizeof(int) * (lines + 1);
    left = realloc(left, new_size);
    right = realloc(right, new_size);
    
    left[lines] = atoi(l);
    right[lines] = atoi(r);

    lines++;
  }

  int total_distance = 0;
  while (lines > 0) {
    int min_l = INT_MAX;
    int ri, li;
    for (int i = 0; i < lines; i++) {
      if (min_l > left[i]) {
        min_l = left[i];
        li = i;
      }
    }

    int min_r = INT_MAX;
    for (int i = 0; i < lines; i++) {
      if (min_r > right[i]) {
        min_r = right[i];
        ri = i;
      }
    }

    total_distance += abs(min_l - min_r);

    left = remove_index(left, li, lines);
    right = remove_index(right, ri, lines);
    lines--;
  }
  printf("%d\n", total_distance);
  return 0;
}

