#include <stdbool.h>

#ifndef _QUEUE_LIB_H
#define _QUEUE_LIB_H

#ifdef __cplusplus
extern "C" {
#endif

extern struct _QueNode* rootNode;

struct _QuedEvent {
	// 0 to 255
	unsigned char power;
	// Time of trigger.
	unsigned long time;
	
};

typedef struct _QuedEvent QuedEvent;

struct _QueNode {
	struct _QueNode* next;
	QuedEvent event;
};

typedef struct _QueNode QueNode;

bool queEvent(QuedEvent event);
bool unqueEvent(unsigned long time, QuedEvent* output);


#ifdef __cplusplus
}
#endif
#endif