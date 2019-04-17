// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: messages.proto

package se.locutus.sea_chauffeur;

public final class Messages {
  private Messages() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  /**
   * Protobuf enum {@code ResponseCode}
   */
  public enum ResponseCode
      implements com.google.protobuf.Internal.EnumLite {
    /**
     * <code>UKNOWN = 0;</code>
     */
    UKNOWN(0),
    /**
     * <code>OK = 1;</code>
     */
    OK(1),
    ;

    /**
     * <code>UKNOWN = 0;</code>
     */
    public static final int UKNOWN_VALUE = 0;
    /**
     * <code>OK = 1;</code>
     */
    public static final int OK_VALUE = 1;


    public final int getNumber() {
      return value;
    }

    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static ResponseCode valueOf(int value) {
      return forNumber(value);
    }

    public static ResponseCode forNumber(int value) {
      switch (value) {
        case 0: return UKNOWN;
        case 1: return OK;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<ResponseCode>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        ResponseCode> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<ResponseCode>() {
            public ResponseCode findValueByNumber(int number) {
              return ResponseCode.forNumber(number);
            }
          };

    private final int value;

    private ResponseCode(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:ResponseCode)
  }

  public interface SeaRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:SeaRequest)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>optional .NavRequest nav_request = 1;</code>
     */
    boolean hasNavRequest();
    /**
     * <code>optional .NavRequest nav_request = 1;</code>
     */
    se.locutus.sea_chauffeur.Messages.NavRequest getNavRequest();
  }
  /**
   * Protobuf type {@code SeaRequest}
   */
  public  static final class SeaRequest extends
      com.google.protobuf.GeneratedMessageLite<
          SeaRequest, SeaRequest.Builder> implements
      // @@protoc_insertion_point(message_implements:SeaRequest)
      SeaRequestOrBuilder {
    private SeaRequest() {
    }
    private int bitField0_;
    public static final int NAV_REQUEST_FIELD_NUMBER = 1;
    private se.locutus.sea_chauffeur.Messages.NavRequest navRequest_;
    /**
     * <code>optional .NavRequest nav_request = 1;</code>
     */
    public boolean hasNavRequest() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional .NavRequest nav_request = 1;</code>
     */
    public se.locutus.sea_chauffeur.Messages.NavRequest getNavRequest() {
      return navRequest_ == null ? se.locutus.sea_chauffeur.Messages.NavRequest.getDefaultInstance() : navRequest_;
    }
    /**
     * <code>optional .NavRequest nav_request = 1;</code>
     */
    private void setNavRequest(se.locutus.sea_chauffeur.Messages.NavRequest value) {
      if (value == null) {
        throw new NullPointerException();
      }
      navRequest_ = value;
      bitField0_ |= 0x00000001;
      }
    /**
     * <code>optional .NavRequest nav_request = 1;</code>
     */
    private void setNavRequest(
        se.locutus.sea_chauffeur.Messages.NavRequest.Builder builderForValue) {
      navRequest_ = builderForValue.build();
      bitField0_ |= 0x00000001;
    }
    /**
     * <code>optional .NavRequest nav_request = 1;</code>
     */
    private void mergeNavRequest(se.locutus.sea_chauffeur.Messages.NavRequest value) {
      if (navRequest_ != null &&
          navRequest_ != se.locutus.sea_chauffeur.Messages.NavRequest.getDefaultInstance()) {
        navRequest_ =
          se.locutus.sea_chauffeur.Messages.NavRequest.newBuilder(navRequest_).mergeFrom(value).buildPartial();
      } else {
        navRequest_ = value;
      }
      bitField0_ |= 0x00000001;
    }
    /**
     * <code>optional .NavRequest nav_request = 1;</code>
     */
    private void clearNavRequest() {  navRequest_ = null;
      bitField0_ = (bitField0_ & ~0x00000001);
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeMessage(1, getNavRequest());
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, getNavRequest());
      }
      size += unknownFields.getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static se.locutus.sea_chauffeur.Messages.SeaRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(se.locutus.sea_chauffeur.Messages.SeaRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    /**
     * Protobuf type {@code SeaRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          se.locutus.sea_chauffeur.Messages.SeaRequest, Builder> implements
        // @@protoc_insertion_point(builder_implements:SeaRequest)
        se.locutus.sea_chauffeur.Messages.SeaRequestOrBuilder {
      // Construct using se.locutus.sea_chauffeur.Messages.SeaRequest.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>optional .NavRequest nav_request = 1;</code>
       */
      public boolean hasNavRequest() {
        return instance.hasNavRequest();
      }
      /**
       * <code>optional .NavRequest nav_request = 1;</code>
       */
      public se.locutus.sea_chauffeur.Messages.NavRequest getNavRequest() {
        return instance.getNavRequest();
      }
      /**
       * <code>optional .NavRequest nav_request = 1;</code>
       */
      public Builder setNavRequest(se.locutus.sea_chauffeur.Messages.NavRequest value) {
        copyOnWrite();
        instance.setNavRequest(value);
        return this;
        }
      /**
       * <code>optional .NavRequest nav_request = 1;</code>
       */
      public Builder setNavRequest(
          se.locutus.sea_chauffeur.Messages.NavRequest.Builder builderForValue) {
        copyOnWrite();
        instance.setNavRequest(builderForValue);
        return this;
      }
      /**
       * <code>optional .NavRequest nav_request = 1;</code>
       */
      public Builder mergeNavRequest(se.locutus.sea_chauffeur.Messages.NavRequest value) {
        copyOnWrite();
        instance.mergeNavRequest(value);
        return this;
      }
      /**
       * <code>optional .NavRequest nav_request = 1;</code>
       */
      public Builder clearNavRequest() {  copyOnWrite();
        instance.clearNavRequest();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:SeaRequest)
    }
    protected final Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        Object arg0, Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new se.locutus.sea_chauffeur.Messages.SeaRequest();
        }
        case IS_INITIALIZED: {
          return DEFAULT_INSTANCE;
        }
        case MAKE_IMMUTABLE: {
          return null;
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case VISIT: {
          Visitor visitor = (Visitor) arg0;
          se.locutus.sea_chauffeur.Messages.SeaRequest other = (se.locutus.sea_chauffeur.Messages.SeaRequest) arg1;
          navRequest_ = visitor.visitMessage(navRequest_, other.navRequest_);
          if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
              .INSTANCE) {
            bitField0_ |= other.bitField0_;
          }
          return this;
        }
        case MERGE_FROM_STREAM: {
          com.google.protobuf.CodedInputStream input =
              (com.google.protobuf.CodedInputStream) arg0;
          com.google.protobuf.ExtensionRegistryLite extensionRegistry =
              (com.google.protobuf.ExtensionRegistryLite) arg1;
          try {
            boolean done = false;
            while (!done) {
              int tag = input.readTag();
              switch (tag) {
                case 0:
                  done = true;
                  break;
                default: {
                  if (!parseUnknownField(tag, input)) {
                    done = true;
                  }
                  break;
                }
                case 10: {
                  se.locutus.sea_chauffeur.Messages.NavRequest.Builder subBuilder = null;
                  if (((bitField0_ & 0x00000001) == 0x00000001)) {
                    subBuilder = navRequest_.toBuilder();
                  }
                  navRequest_ = input.readMessage(se.locutus.sea_chauffeur.Messages.NavRequest.parser(), extensionRegistry);
                  if (subBuilder != null) {
                    subBuilder.mergeFrom(navRequest_);
                    navRequest_ = subBuilder.buildPartial();
                  }
                  bitField0_ |= 0x00000001;
                  break;
                }
              }
            }
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw new RuntimeException(e.setUnfinishedMessage(this));
          } catch (java.io.IOException e) {
            throw new RuntimeException(
                new com.google.protobuf.InvalidProtocolBufferException(
                    e.getMessage()).setUnfinishedMessage(this));
          } finally {
          }
        }
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          if (PARSER == null) {    synchronized (se.locutus.sea_chauffeur.Messages.SeaRequest.class) {
              if (PARSER == null) {
                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
              }
            }
          }
          return PARSER;
        }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:SeaRequest)
    private static final se.locutus.sea_chauffeur.Messages.SeaRequest DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new SeaRequest();
      DEFAULT_INSTANCE.makeImmutable();
    }

    public static se.locutus.sea_chauffeur.Messages.SeaRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<SeaRequest> PARSER;

    public static com.google.protobuf.Parser<SeaRequest> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }

  public interface SeaResponseOrBuilder extends
      // @@protoc_insertion_point(interface_extends:SeaResponse)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>optional .ResponseCode response_code = 1;</code>
     */
    boolean hasResponseCode();
    /**
     * <code>optional .ResponseCode response_code = 1;</code>
     */
    se.locutus.sea_chauffeur.Messages.ResponseCode getResponseCode();
  }
  /**
   * Protobuf type {@code SeaResponse}
   */
  public  static final class SeaResponse extends
      com.google.protobuf.GeneratedMessageLite<
          SeaResponse, SeaResponse.Builder> implements
      // @@protoc_insertion_point(message_implements:SeaResponse)
      SeaResponseOrBuilder {
    private SeaResponse() {
    }
    private int bitField0_;
    public static final int RESPONSE_CODE_FIELD_NUMBER = 1;
    private int responseCode_;
    /**
     * <code>optional .ResponseCode response_code = 1;</code>
     */
    public boolean hasResponseCode() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional .ResponseCode response_code = 1;</code>
     */
    public se.locutus.sea_chauffeur.Messages.ResponseCode getResponseCode() {
      se.locutus.sea_chauffeur.Messages.ResponseCode result = se.locutus.sea_chauffeur.Messages.ResponseCode.forNumber(responseCode_);
      return result == null ? se.locutus.sea_chauffeur.Messages.ResponseCode.UKNOWN : result;
    }
    /**
     * <code>optional .ResponseCode response_code = 1;</code>
     */
    private void setResponseCode(se.locutus.sea_chauffeur.Messages.ResponseCode value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000001;
      responseCode_ = value.getNumber();
    }
    /**
     * <code>optional .ResponseCode response_code = 1;</code>
     */
    private void clearResponseCode() {
      bitField0_ = (bitField0_ & ~0x00000001);
      responseCode_ = 0;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeEnum(1, responseCode_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(1, responseCode_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static se.locutus.sea_chauffeur.Messages.SeaResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static se.locutus.sea_chauffeur.Messages.SeaResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(se.locutus.sea_chauffeur.Messages.SeaResponse prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    /**
     * Protobuf type {@code SeaResponse}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          se.locutus.sea_chauffeur.Messages.SeaResponse, Builder> implements
        // @@protoc_insertion_point(builder_implements:SeaResponse)
        se.locutus.sea_chauffeur.Messages.SeaResponseOrBuilder {
      // Construct using se.locutus.sea_chauffeur.Messages.SeaResponse.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>optional .ResponseCode response_code = 1;</code>
       */
      public boolean hasResponseCode() {
        return instance.hasResponseCode();
      }
      /**
       * <code>optional .ResponseCode response_code = 1;</code>
       */
      public se.locutus.sea_chauffeur.Messages.ResponseCode getResponseCode() {
        return instance.getResponseCode();
      }
      /**
       * <code>optional .ResponseCode response_code = 1;</code>
       */
      public Builder setResponseCode(se.locutus.sea_chauffeur.Messages.ResponseCode value) {
        copyOnWrite();
        instance.setResponseCode(value);
        return this;
      }
      /**
       * <code>optional .ResponseCode response_code = 1;</code>
       */
      public Builder clearResponseCode() {
        copyOnWrite();
        instance.clearResponseCode();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:SeaResponse)
    }
    protected final Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        Object arg0, Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new se.locutus.sea_chauffeur.Messages.SeaResponse();
        }
        case IS_INITIALIZED: {
          return DEFAULT_INSTANCE;
        }
        case MAKE_IMMUTABLE: {
          return null;
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case VISIT: {
          Visitor visitor = (Visitor) arg0;
          se.locutus.sea_chauffeur.Messages.SeaResponse other = (se.locutus.sea_chauffeur.Messages.SeaResponse) arg1;
          responseCode_ = visitor.visitInt(hasResponseCode(), responseCode_,
              other.hasResponseCode(), other.responseCode_);
          if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
              .INSTANCE) {
            bitField0_ |= other.bitField0_;
          }
          return this;
        }
        case MERGE_FROM_STREAM: {
          com.google.protobuf.CodedInputStream input =
              (com.google.protobuf.CodedInputStream) arg0;
          com.google.protobuf.ExtensionRegistryLite extensionRegistry =
              (com.google.protobuf.ExtensionRegistryLite) arg1;
          try {
            boolean done = false;
            while (!done) {
              int tag = input.readTag();
              switch (tag) {
                case 0:
                  done = true;
                  break;
                default: {
                  if (!parseUnknownField(tag, input)) {
                    done = true;
                  }
                  break;
                }
                case 8: {
                  int rawValue = input.readEnum();
                  se.locutus.sea_chauffeur.Messages.ResponseCode value = se.locutus.sea_chauffeur.Messages.ResponseCode.forNumber(rawValue);
                  if (value == null) {
                    super.mergeVarintField(1, rawValue);
                  } else {
                    bitField0_ |= 0x00000001;
                    responseCode_ = rawValue;
                  }
                  break;
                }
              }
            }
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw new RuntimeException(e.setUnfinishedMessage(this));
          } catch (java.io.IOException e) {
            throw new RuntimeException(
                new com.google.protobuf.InvalidProtocolBufferException(
                    e.getMessage()).setUnfinishedMessage(this));
          } finally {
          }
        }
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          if (PARSER == null) {    synchronized (se.locutus.sea_chauffeur.Messages.SeaResponse.class) {
              if (PARSER == null) {
                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
              }
            }
          }
          return PARSER;
        }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:SeaResponse)
    private static final se.locutus.sea_chauffeur.Messages.SeaResponse DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new SeaResponse();
      DEFAULT_INSTANCE.makeImmutable();
    }

    public static se.locutus.sea_chauffeur.Messages.SeaResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<SeaResponse> PARSER;

    public static com.google.protobuf.Parser<SeaResponse> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }

  public interface NavRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:NavRequest)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>optional float lat = 1;</code>
     */
    boolean hasLat();
    /**
     * <code>optional float lat = 1;</code>
     */
    float getLat();

    /**
     * <code>optional float lng = 2;</code>
     */
    boolean hasLng();
    /**
     * <code>optional float lng = 2;</code>
     */
    float getLng();
  }
  /**
   * Protobuf type {@code NavRequest}
   */
  public  static final class NavRequest extends
      com.google.protobuf.GeneratedMessageLite<
          NavRequest, NavRequest.Builder> implements
      // @@protoc_insertion_point(message_implements:NavRequest)
      NavRequestOrBuilder {
    private NavRequest() {
    }
    private int bitField0_;
    public static final int LAT_FIELD_NUMBER = 1;
    private float lat_;
    /**
     * <code>optional float lat = 1;</code>
     */
    public boolean hasLat() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional float lat = 1;</code>
     */
    public float getLat() {
      return lat_;
    }
    /**
     * <code>optional float lat = 1;</code>
     */
    private void setLat(float value) {
      bitField0_ |= 0x00000001;
      lat_ = value;
    }
    /**
     * <code>optional float lat = 1;</code>
     */
    private void clearLat() {
      bitField0_ = (bitField0_ & ~0x00000001);
      lat_ = 0F;
    }

    public static final int LNG_FIELD_NUMBER = 2;
    private float lng_;
    /**
     * <code>optional float lng = 2;</code>
     */
    public boolean hasLng() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional float lng = 2;</code>
     */
    public float getLng() {
      return lng_;
    }
    /**
     * <code>optional float lng = 2;</code>
     */
    private void setLng(float value) {
      bitField0_ |= 0x00000002;
      lng_ = value;
    }
    /**
     * <code>optional float lng = 2;</code>
     */
    private void clearLng() {
      bitField0_ = (bitField0_ & ~0x00000002);
      lng_ = 0F;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeFloat(1, lat_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeFloat(2, lng_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(1, lat_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(2, lng_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static se.locutus.sea_chauffeur.Messages.NavRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static se.locutus.sea_chauffeur.Messages.NavRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static se.locutus.sea_chauffeur.Messages.NavRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static se.locutus.sea_chauffeur.Messages.NavRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static se.locutus.sea_chauffeur.Messages.NavRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static se.locutus.sea_chauffeur.Messages.NavRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static se.locutus.sea_chauffeur.Messages.NavRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static se.locutus.sea_chauffeur.Messages.NavRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static se.locutus.sea_chauffeur.Messages.NavRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static se.locutus.sea_chauffeur.Messages.NavRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(se.locutus.sea_chauffeur.Messages.NavRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    /**
     * Protobuf type {@code NavRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          se.locutus.sea_chauffeur.Messages.NavRequest, Builder> implements
        // @@protoc_insertion_point(builder_implements:NavRequest)
        se.locutus.sea_chauffeur.Messages.NavRequestOrBuilder {
      // Construct using se.locutus.sea_chauffeur.Messages.NavRequest.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>optional float lat = 1;</code>
       */
      public boolean hasLat() {
        return instance.hasLat();
      }
      /**
       * <code>optional float lat = 1;</code>
       */
      public float getLat() {
        return instance.getLat();
      }
      /**
       * <code>optional float lat = 1;</code>
       */
      public Builder setLat(float value) {
        copyOnWrite();
        instance.setLat(value);
        return this;
      }
      /**
       * <code>optional float lat = 1;</code>
       */
      public Builder clearLat() {
        copyOnWrite();
        instance.clearLat();
        return this;
      }

      /**
       * <code>optional float lng = 2;</code>
       */
      public boolean hasLng() {
        return instance.hasLng();
      }
      /**
       * <code>optional float lng = 2;</code>
       */
      public float getLng() {
        return instance.getLng();
      }
      /**
       * <code>optional float lng = 2;</code>
       */
      public Builder setLng(float value) {
        copyOnWrite();
        instance.setLng(value);
        return this;
      }
      /**
       * <code>optional float lng = 2;</code>
       */
      public Builder clearLng() {
        copyOnWrite();
        instance.clearLng();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:NavRequest)
    }
    protected final Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        Object arg0, Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new se.locutus.sea_chauffeur.Messages.NavRequest();
        }
        case IS_INITIALIZED: {
          return DEFAULT_INSTANCE;
        }
        case MAKE_IMMUTABLE: {
          return null;
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case VISIT: {
          Visitor visitor = (Visitor) arg0;
          se.locutus.sea_chauffeur.Messages.NavRequest other = (se.locutus.sea_chauffeur.Messages.NavRequest) arg1;
          lat_ = visitor.visitFloat(
              hasLat(), lat_,
              other.hasLat(), other.lat_);
          lng_ = visitor.visitFloat(
              hasLng(), lng_,
              other.hasLng(), other.lng_);
          if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
              .INSTANCE) {
            bitField0_ |= other.bitField0_;
          }
          return this;
        }
        case MERGE_FROM_STREAM: {
          com.google.protobuf.CodedInputStream input =
              (com.google.protobuf.CodedInputStream) arg0;
          com.google.protobuf.ExtensionRegistryLite extensionRegistry =
              (com.google.protobuf.ExtensionRegistryLite) arg1;
          try {
            boolean done = false;
            while (!done) {
              int tag = input.readTag();
              switch (tag) {
                case 0:
                  done = true;
                  break;
                default: {
                  if (!parseUnknownField(tag, input)) {
                    done = true;
                  }
                  break;
                }
                case 13: {
                  bitField0_ |= 0x00000001;
                  lat_ = input.readFloat();
                  break;
                }
                case 21: {
                  bitField0_ |= 0x00000002;
                  lng_ = input.readFloat();
                  break;
                }
              }
            }
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw new RuntimeException(e.setUnfinishedMessage(this));
          } catch (java.io.IOException e) {
            throw new RuntimeException(
                new com.google.protobuf.InvalidProtocolBufferException(
                    e.getMessage()).setUnfinishedMessage(this));
          } finally {
          }
        }
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          if (PARSER == null) {    synchronized (se.locutus.sea_chauffeur.Messages.NavRequest.class) {
              if (PARSER == null) {
                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
              }
            }
          }
          return PARSER;
        }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:NavRequest)
    private static final se.locutus.sea_chauffeur.Messages.NavRequest DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new NavRequest();
      DEFAULT_INSTANCE.makeImmutable();
    }

    public static se.locutus.sea_chauffeur.Messages.NavRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<NavRequest> PARSER;

    public static com.google.protobuf.Parser<NavRequest> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}
