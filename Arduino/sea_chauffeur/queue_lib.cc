#include <stdbool.h>
#include <stdlib.h>
#include "queue_lib.h"

#ifdef __cplusplus
extern "C" {
#endif

QueNode* rootNode = 0;


bool queEvent(QuedEvent event) {
	if (!rootNode) {
		rootNode = (QueNode*)malloc(sizeof(QueNode));
		rootNode->event = event;
		rootNode->next = 0;
		return true;
	}
	QueNode* currentNode = rootNode;
	while (currentNode->next) {
		currentNode = currentNode->next;
	}
	currentNode->next = (QueNode*)malloc(sizeof(QueNode));
	currentNode->next->event = event;
	currentNode->next->next = 0;		
	return true;
}

bool unqueEvent(unsigned long time, QuedEvent* output) {
	QueNode* currentNode = rootNode;
	if (currentNode->event.time <= time) {
		*output = currentNode->event;
		return true;
	}
	return false;
}


#ifdef __cplusplus
}
#endif