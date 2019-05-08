#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include "queue_lib.h"

#ifdef __cplusplus
extern "C" {
#endif

QueNode* rootNode = 0;


bool queEvent(QuedEvent event) {
	QueNode** prevNodePointer = &rootNode;
	while (*prevNodePointer) {
		prevNodePointer = &(*prevNodePointer)->next;
	}
	*prevNodePointer = (QueNode*)malloc(sizeof(QueNode));
	(*prevNodePointer)->event = event;
	(*prevNodePointer)->next = 0;
	return true;
}

bool unqueEvent(unsigned long time, QuedEvent* output) {
	QueNode* currentNode = rootNode;
	QueNode** prevNodePointer = &rootNode;
	
	while (currentNode && currentNode->event.time > time) {
		currentNode = currentNode->next;
		prevNodePointer = &currentNode->next;
	}
	
	if (currentNode && currentNode->event.time <= time) {
		*output = currentNode->event;
		*prevNodePointer = currentNode->next;
		free(currentNode);
		return true;
	}
	return false;
}


#ifdef __cplusplus
}
#endif