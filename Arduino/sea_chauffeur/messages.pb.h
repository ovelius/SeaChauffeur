/* Automatically generated nanopb header */
/* Generated by nanopb-0.3.9.3 at Fri Apr 12 23:24:24 2019. */

#ifndef PB_MESSAGES_PB_H_INCLUDED
#define PB_MESSAGES_PB_H_INCLUDED
#include <pb.h>

/* @@protoc_insertion_point(includes) */
#if PB_PROTO_HEADER_VERSION != 30
#error Regenerate this file with the current version of nanopb generator.
#endif

#ifdef __cplusplus
extern "C" {
#endif

/* Struct definitions */
typedef struct _SeaResponse {
    char dummy_field;
/* @@protoc_insertion_point(struct:SeaResponse) */
} SeaResponse;

typedef struct _NavRequest {
    bool has_lat;
    float lat;
    bool has_lng;
    float lng;
/* @@protoc_insertion_point(struct:NavRequest) */
} NavRequest;

typedef struct _SeaRequest {
    bool has_nav_request;
    NavRequest nav_request;
/* @@protoc_insertion_point(struct:SeaRequest) */
} SeaRequest;

/* Default values for struct fields */

/* Initializer values for message structs */
#define SeaRequest_init_default                  {false, NavRequest_init_default}
#define SeaResponse_init_default                 {0}
#define NavRequest_init_default                  {false, 0, false, 0}
#define SeaRequest_init_zero                     {false, NavRequest_init_zero}
#define SeaResponse_init_zero                    {0}
#define NavRequest_init_zero                     {false, 0, false, 0}

/* Field tags (for use in manual encoding/decoding) */
#define NavRequest_lat_tag                       1
#define NavRequest_lng_tag                       2
#define SeaRequest_nav_request_tag               1

/* Struct field encoding specification for nanopb */
extern const pb_field_t SeaRequest_fields[2];
extern const pb_field_t SeaResponse_fields[1];
extern const pb_field_t NavRequest_fields[3];

/* Maximum encoded size of messages (where known) */
#define SeaRequest_size                          12
#define SeaResponse_size                         0
#define NavRequest_size                          10

/* Message IDs (where set with "msgid" option) */
#ifdef PB_MSGID

#define MESSAGES_MESSAGES \


#endif

#ifdef __cplusplus
} /* extern "C" */
#endif
/* @@protoc_insertion_point(eof) */

#endif