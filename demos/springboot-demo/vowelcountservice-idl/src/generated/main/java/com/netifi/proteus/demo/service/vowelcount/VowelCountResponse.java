// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: vowelcount.proto

package com.netifi.proteus.demo.service.vowelcount;

/**
 * Protobuf type {@code com.netifi.proteus.demo.service.vowelcount.VowelCountResponse}
 */
public  final class VowelCountResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.netifi.proteus.demo.service.vowelcount.VowelCountResponse)
    VowelCountResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use VowelCountResponse.newBuilder() to construct.
  private VowelCountResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private VowelCountResponse() {
    vowelCnt_ = 0L;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private VowelCountResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 8: {

            vowelCnt_ = input.readInt64();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.netifi.proteus.demo.service.vowelcount.VowelCountServiceProto.internal_static_com_netifi_proteus_demo_service_vowelcount_VowelCountResponse_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.netifi.proteus.demo.service.vowelcount.VowelCountServiceProto.internal_static_com_netifi_proteus_demo_service_vowelcount_VowelCountResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.netifi.proteus.demo.service.vowelcount.VowelCountResponse.class, com.netifi.proteus.demo.service.vowelcount.VowelCountResponse.Builder.class);
  }

  public static final int VOWELCNT_FIELD_NUMBER = 1;
  private long vowelCnt_;
  /**
   * <code>int64 vowelCnt = 1;</code>
   */
  public long getVowelCnt() {
    return vowelCnt_;
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (vowelCnt_ != 0L) {
      output.writeInt64(1, vowelCnt_);
    }
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (vowelCnt_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(1, vowelCnt_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.netifi.proteus.demo.service.vowelcount.VowelCountResponse)) {
      return super.equals(obj);
    }
    com.netifi.proteus.demo.service.vowelcount.VowelCountResponse other = (com.netifi.proteus.demo.service.vowelcount.VowelCountResponse) obj;

    boolean result = true;
    result = result && (getVowelCnt()
        == other.getVowelCnt());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + VOWELCNT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getVowelCnt());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.netifi.proteus.demo.service.vowelcount.VowelCountResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.netifi.proteus.demo.service.vowelcount.VowelCountResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.netifi.proteus.demo.service.vowelcount.VowelCountResponse)
      com.netifi.proteus.demo.service.vowelcount.VowelCountResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.netifi.proteus.demo.service.vowelcount.VowelCountServiceProto.internal_static_com_netifi_proteus_demo_service_vowelcount_VowelCountResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.netifi.proteus.demo.service.vowelcount.VowelCountServiceProto.internal_static_com_netifi_proteus_demo_service_vowelcount_VowelCountResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.netifi.proteus.demo.service.vowelcount.VowelCountResponse.class, com.netifi.proteus.demo.service.vowelcount.VowelCountResponse.Builder.class);
    }

    // Construct using com.netifi.proteus.demo.service.vowelcount.VowelCountResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      vowelCnt_ = 0L;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.netifi.proteus.demo.service.vowelcount.VowelCountServiceProto.internal_static_com_netifi_proteus_demo_service_vowelcount_VowelCountResponse_descriptor;
    }

    public com.netifi.proteus.demo.service.vowelcount.VowelCountResponse getDefaultInstanceForType() {
      return com.netifi.proteus.demo.service.vowelcount.VowelCountResponse.getDefaultInstance();
    }

    public com.netifi.proteus.demo.service.vowelcount.VowelCountResponse build() {
      com.netifi.proteus.demo.service.vowelcount.VowelCountResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.netifi.proteus.demo.service.vowelcount.VowelCountResponse buildPartial() {
      com.netifi.proteus.demo.service.vowelcount.VowelCountResponse result = new com.netifi.proteus.demo.service.vowelcount.VowelCountResponse(this);
      result.vowelCnt_ = vowelCnt_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.netifi.proteus.demo.service.vowelcount.VowelCountResponse) {
        return mergeFrom((com.netifi.proteus.demo.service.vowelcount.VowelCountResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.netifi.proteus.demo.service.vowelcount.VowelCountResponse other) {
      if (other == com.netifi.proteus.demo.service.vowelcount.VowelCountResponse.getDefaultInstance()) return this;
      if (other.getVowelCnt() != 0L) {
        setVowelCnt(other.getVowelCnt());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.netifi.proteus.demo.service.vowelcount.VowelCountResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.netifi.proteus.demo.service.vowelcount.VowelCountResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long vowelCnt_ ;
    /**
     * <code>int64 vowelCnt = 1;</code>
     */
    public long getVowelCnt() {
      return vowelCnt_;
    }
    /**
     * <code>int64 vowelCnt = 1;</code>
     */
    public Builder setVowelCnt(long value) {
      
      vowelCnt_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 vowelCnt = 1;</code>
     */
    public Builder clearVowelCnt() {
      
      vowelCnt_ = 0L;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:com.netifi.proteus.demo.service.vowelcount.VowelCountResponse)
  }

  // @@protoc_insertion_point(class_scope:com.netifi.proteus.demo.service.vowelcount.VowelCountResponse)
  private static final com.netifi.proteus.demo.service.vowelcount.VowelCountResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.netifi.proteus.demo.service.vowelcount.VowelCountResponse();
  }

  public static com.netifi.proteus.demo.service.vowelcount.VowelCountResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<VowelCountResponse>
      PARSER = new com.google.protobuf.AbstractParser<VowelCountResponse>() {
    public VowelCountResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new VowelCountResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<VowelCountResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<VowelCountResponse> getParserForType() {
    return PARSER;
  }

  public com.netifi.proteus.demo.service.vowelcount.VowelCountResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

