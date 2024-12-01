#include <stdio.h>
#include <stdlib.h>
#include <string.h>

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

  int total_similiarity = 0;
  for (int i = 0; i < lines; i++) {
    int count = 0;
    for (int j = 0; j < lines; j++) {
      if (right[j] == left[i]) count++; 
    }
    total_similiarity += left[i] * count;
  }
  printf("%d\n", total_similiarity);
  return 0;
}
