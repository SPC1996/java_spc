// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: java_spc/netty/serialize/protobuf/proto/RespInfoProto.proto

package java_spc.netty.serialize.protobuf;

public final class RespInfo {
	private RespInfo() {
	}

	public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {
	}

	public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
		registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
	}

	public interface RespInfoProtoOrBuilder extends
			// @@protoc_insertion_point(interface_extends:RespInfoProto)
			com.google.protobuf.MessageOrBuilder {

		/**
		 * <code>required int32 id = 1;</code>
		 */
		boolean hasId();

		/**
		 * <code>required int32 id = 1;</code>
		 */
		int getId();

		/**
		 * <code>required int32 code = 2;</code>
		 */
		boolean hasCode();

		/**
		 * <code>required int32 code = 2;</code>
		 */
		int getCode();

		/**
		 * <code>required string desc = 3;</code>
		 */
		boolean hasDesc();

		/**
		 * <code>required string desc = 3;</code>
		 */
		java.lang.String getDesc();

		/**
		 * <code>required string desc = 3;</code>
		 */
		com.google.protobuf.ByteString getDescBytes();
	}

	/**
	 * Protobuf type {@code RespInfoProto}
	 */
	public static final class RespInfoProto extends com.google.protobuf.GeneratedMessageV3 implements
			// @@protoc_insertion_point(message_implements:RespInfoProto)
			RespInfoProtoOrBuilder {
		// Use RespInfoProto.newBuilder() to construct.
		private RespInfoProto(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
			super(builder);
		}

		private RespInfoProto() {
			id_ = 0;
			code_ = 0;
			desc_ = "";
		}

		@java.lang.Override
		public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
			return this.unknownFields;
		}

		private RespInfoProto(com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			this();
			@SuppressWarnings("unused")
			int mutable_bitField0_ = 0;
			com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet
					.newBuilder();
			try {
				boolean done = false;
				while (!done) {
					int tag = input.readTag();
					switch (tag) {
					case 0:
						done = true;
						break;
					default: {
						if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
							done = true;
						}
						break;
					}
					case 8: {
						bitField0_ |= 0x00000001;
						id_ = input.readInt32();
						break;
					}
					case 16: {
						bitField0_ |= 0x00000002;
						code_ = input.readInt32();
						break;
					}
					case 26: {
						com.google.protobuf.ByteString bs = input.readBytes();
						bitField0_ |= 0x00000004;
						desc_ = bs;
						break;
					}
					}
				}
			} catch (com.google.protobuf.InvalidProtocolBufferException e) {
				throw e.setUnfinishedMessage(this);
			} catch (java.io.IOException e) {
				throw new com.google.protobuf.InvalidProtocolBufferException(e).setUnfinishedMessage(this);
			} finally {
				this.unknownFields = unknownFields.build();
				makeExtensionsImmutable();
			}
		}

		public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
			return java_spc.netty.serialize.protobuf.RespInfo.internal_static_RespInfoProto_descriptor;
		}

		protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
			return java_spc.netty.serialize.protobuf.RespInfo.internal_static_RespInfoProto_fieldAccessorTable
					.ensureFieldAccessorsInitialized(java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto.class,
							java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto.Builder.class);
		}

		private int bitField0_;
		public static final int ID_FIELD_NUMBER = 1;
		private int id_;

		/**
		 * <code>required int32 id = 1;</code>
		 */
		public boolean hasId() {
			return ((bitField0_ & 0x00000001) == 0x00000001);
		}

		/**
		 * <code>required int32 id = 1;</code>
		 */
		public int getId() {
			return id_;
		}

		public static final int CODE_FIELD_NUMBER = 2;
		private int code_;

		/**
		 * <code>required int32 code = 2;</code>
		 */
		public boolean hasCode() {
			return ((bitField0_ & 0x00000002) == 0x00000002);
		}

		/**
		 * <code>required int32 code = 2;</code>
		 */
		public int getCode() {
			return code_;
		}

		public static final int DESC_FIELD_NUMBER = 3;
		private volatile java.lang.Object desc_;

		/**
		 * <code>required string desc = 3;</code>
		 */
		public boolean hasDesc() {
			return ((bitField0_ & 0x00000004) == 0x00000004);
		}

		/**
		 * <code>required string desc = 3;</code>
		 */
		public java.lang.String getDesc() {
			java.lang.Object ref = desc_;
			if (ref instanceof java.lang.String) {
				return (java.lang.String) ref;
			} else {
				com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
				java.lang.String s = bs.toStringUtf8();
				if (bs.isValidUtf8()) {
					desc_ = s;
				}
				return s;
			}
		}

		/**
		 * <code>required string desc = 3;</code>
		 */
		public com.google.protobuf.ByteString getDescBytes() {
			java.lang.Object ref = desc_;
			if (ref instanceof java.lang.String) {
				com.google.protobuf.ByteString b = com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
				desc_ = b;
				return b;
			} else {
				return (com.google.protobuf.ByteString) ref;
			}
		}

		private byte memoizedIsInitialized = -1;

		public final boolean isInitialized() {
			byte isInitialized = memoizedIsInitialized;
			if (isInitialized == 1)
				return true;
			if (isInitialized == 0)
				return false;

			if (!hasId()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasCode()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasDesc()) {
				memoizedIsInitialized = 0;
				return false;
			}
			memoizedIsInitialized = 1;
			return true;
		}

		public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
			if (((bitField0_ & 0x00000001) == 0x00000001)) {
				output.writeInt32(1, id_);
			}
			if (((bitField0_ & 0x00000002) == 0x00000002)) {
				output.writeInt32(2, code_);
			}
			if (((bitField0_ & 0x00000004) == 0x00000004)) {
				com.google.protobuf.GeneratedMessageV3.writeString(output, 3, desc_);
			}
			unknownFields.writeTo(output);
		}

		public int getSerializedSize() {
			int size = memoizedSize;
			if (size != -1)
				return size;

			size = 0;
			if (((bitField0_ & 0x00000001) == 0x00000001)) {
				size += com.google.protobuf.CodedOutputStream.computeInt32Size(1, id_);
			}
			if (((bitField0_ & 0x00000002) == 0x00000002)) {
				size += com.google.protobuf.CodedOutputStream.computeInt32Size(2, code_);
			}
			if (((bitField0_ & 0x00000004) == 0x00000004)) {
				size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, desc_);
			}
			size += unknownFields.getSerializedSize();
			memoizedSize = size;
			return size;
		}

		private static final long serialVersionUID = 0L;

		@java.lang.Override
		public boolean equals(final java.lang.Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto)) {
				return super.equals(obj);
			}
			java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto other = (java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto) obj;

			boolean result = true;
			result = result && (hasId() == other.hasId());
			if (hasId()) {
				result = result && (getId() == other.getId());
			}
			result = result && (hasCode() == other.hasCode());
			if (hasCode()) {
				result = result && (getCode() == other.getCode());
			}
			result = result && (hasDesc() == other.hasDesc());
			if (hasDesc()) {
				result = result && getDesc().equals(other.getDesc());
			}
			result = result && unknownFields.equals(other.unknownFields);
			return result;
		}

		@SuppressWarnings("unchecked")
		@java.lang.Override
		public int hashCode() {
			if (memoizedHashCode != 0) {
				return memoizedHashCode;
			}
			int hash = 41;
			hash = (19 * hash) + getDescriptor().hashCode();
			if (hasId()) {
				hash = (37 * hash) + ID_FIELD_NUMBER;
				hash = (53 * hash) + getId();
			}
			if (hasCode()) {
				hash = (37 * hash) + CODE_FIELD_NUMBER;
				hash = (53 * hash) + getCode();
			}
			if (hasDesc()) {
				hash = (37 * hash) + DESC_FIELD_NUMBER;
				hash = (53 * hash) + getDesc().hashCode();
			}
			hash = (29 * hash) + unknownFields.hashCode();
			memoizedHashCode = hash;
			return hash;
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parseFrom(java.nio.ByteBuffer data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parseFrom(java.nio.ByteBuffer data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parseFrom(
				com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parseFrom(
				com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parseFrom(byte[] data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parseFrom(byte[] data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parseFrom(java.io.InputStream input)
				throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parseFrom(java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parseDelimitedFrom(
				java.io.InputStream input) throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parseDelimitedFrom(
				java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input,
					extensionRegistry);
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parseFrom(
				com.google.protobuf.CodedInputStream input) throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parseFrom(
				com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
		}

		public Builder newBuilderForType() {
			return newBuilder();
		}

		public static Builder newBuilder() {
			return DEFAULT_INSTANCE.toBuilder();
		}

		public static Builder newBuilder(java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto prototype) {
			return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
		}

		public Builder toBuilder() {
			return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
		}

		@java.lang.Override
		protected Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
			Builder builder = new Builder(parent);
			return builder;
		}

		/**
		 * Protobuf type {@code RespInfoProto}
		 */
		public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
				// @@protoc_insertion_point(builder_implements:RespInfoProto)
				java_spc.netty.serialize.protobuf.RespInfo.RespInfoProtoOrBuilder {
			public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
				return java_spc.netty.serialize.protobuf.RespInfo.internal_static_RespInfoProto_descriptor;
			}

			protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
				return java_spc.netty.serialize.protobuf.RespInfo.internal_static_RespInfoProto_fieldAccessorTable
						.ensureFieldAccessorsInitialized(java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto.class,
								java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto.Builder.class);
			}

			// Construct using
			// java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto.newBuilder()
			private Builder() {
				maybeForceBuilderInitialization();
			}

			private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
				super(parent);
				maybeForceBuilderInitialization();
			}

			private void maybeForceBuilderInitialization() {
				if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
				}
			}

			public Builder clear() {
				super.clear();
				id_ = 0;
				bitField0_ = (bitField0_ & ~0x00000001);
				code_ = 0;
				bitField0_ = (bitField0_ & ~0x00000002);
				desc_ = "";
				bitField0_ = (bitField0_ & ~0x00000004);
				return this;
			}

			public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
				return java_spc.netty.serialize.protobuf.RespInfo.internal_static_RespInfoProto_descriptor;
			}

			public java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto getDefaultInstanceForType() {
				return java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto.getDefaultInstance();
			}

			public java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto build() {
				java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto result = buildPartial();
				if (!result.isInitialized()) {
					throw newUninitializedMessageException(result);
				}
				return result;
			}

			public java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto buildPartial() {
				java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto result = new java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto(
						this);
				int from_bitField0_ = bitField0_;
				int to_bitField0_ = 0;
				if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
					to_bitField0_ |= 0x00000001;
				}
				result.id_ = id_;
				if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
					to_bitField0_ |= 0x00000002;
				}
				result.code_ = code_;
				if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
					to_bitField0_ |= 0x00000004;
				}
				result.desc_ = desc_;
				result.bitField0_ = to_bitField0_;
				onBuilt();
				return result;
			}

			public Builder clone() {
				return (Builder) super.clone();
			}

			public Builder setField(com.google.protobuf.Descriptors.FieldDescriptor field, Object value) {
				return (Builder) super.setField(field, value);
			}

			public Builder clearField(com.google.protobuf.Descriptors.FieldDescriptor field) {
				return (Builder) super.clearField(field);
			}

			public Builder clearOneof(com.google.protobuf.Descriptors.OneofDescriptor oneof) {
				return (Builder) super.clearOneof(oneof);
			}

			public Builder setRepeatedField(com.google.protobuf.Descriptors.FieldDescriptor field, int index,
					Object value) {
				return (Builder) super.setRepeatedField(field, index, value);
			}

			public Builder addRepeatedField(com.google.protobuf.Descriptors.FieldDescriptor field, Object value) {
				return (Builder) super.addRepeatedField(field, value);
			}

			public Builder mergeFrom(com.google.protobuf.Message other) {
				if (other instanceof java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto) {
					return mergeFrom((java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto) other);
				} else {
					super.mergeFrom(other);
					return this;
				}
			}

			public Builder mergeFrom(java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto other) {
				if (other == java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto.getDefaultInstance())
					return this;
				if (other.hasId()) {
					setId(other.getId());
				}
				if (other.hasCode()) {
					setCode(other.getCode());
				}
				if (other.hasDesc()) {
					bitField0_ |= 0x00000004;
					desc_ = other.desc_;
					onChanged();
				}
				this.mergeUnknownFields(other.unknownFields);
				onChanged();
				return this;
			}

			public final boolean isInitialized() {
				if (!hasId()) {
					return false;
				}
				if (!hasCode()) {
					return false;
				}
				if (!hasDesc()) {
					return false;
				}
				return true;
			}

			public Builder mergeFrom(com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
				java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto parsedMessage = null;
				try {
					parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
				} catch (com.google.protobuf.InvalidProtocolBufferException e) {
					parsedMessage = (java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto) e.getUnfinishedMessage();
					throw e.unwrapIOException();
				} finally {
					if (parsedMessage != null) {
						mergeFrom(parsedMessage);
					}
				}
				return this;
			}

			private int bitField0_;

			private int id_;

			/**
			 * <code>required int32 id = 1;</code>
			 */
			public boolean hasId() {
				return ((bitField0_ & 0x00000001) == 0x00000001);
			}

			/**
			 * <code>required int32 id = 1;</code>
			 */
			public int getId() {
				return id_;
			}

			/**
			 * <code>required int32 id = 1;</code>
			 */
			public Builder setId(int value) {
				bitField0_ |= 0x00000001;
				id_ = value;
				onChanged();
				return this;
			}

			/**
			 * <code>required int32 id = 1;</code>
			 */
			public Builder clearId() {
				bitField0_ = (bitField0_ & ~0x00000001);
				id_ = 0;
				onChanged();
				return this;
			}

			private int code_;

			/**
			 * <code>required int32 code = 2;</code>
			 */
			public boolean hasCode() {
				return ((bitField0_ & 0x00000002) == 0x00000002);
			}

			/**
			 * <code>required int32 code = 2;</code>
			 */
			public int getCode() {
				return code_;
			}

			/**
			 * <code>required int32 code = 2;</code>
			 */
			public Builder setCode(int value) {
				bitField0_ |= 0x00000002;
				code_ = value;
				onChanged();
				return this;
			}

			/**
			 * <code>required int32 code = 2;</code>
			 */
			public Builder clearCode() {
				bitField0_ = (bitField0_ & ~0x00000002);
				code_ = 0;
				onChanged();
				return this;
			}

			private java.lang.Object desc_ = "";

			/**
			 * <code>required string desc = 3;</code>
			 */
			public boolean hasDesc() {
				return ((bitField0_ & 0x00000004) == 0x00000004);
			}

			/**
			 * <code>required string desc = 3;</code>
			 */
			public java.lang.String getDesc() {
				java.lang.Object ref = desc_;
				if (!(ref instanceof java.lang.String)) {
					com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
					java.lang.String s = bs.toStringUtf8();
					if (bs.isValidUtf8()) {
						desc_ = s;
					}
					return s;
				} else {
					return (java.lang.String) ref;
				}
			}

			/**
			 * <code>required string desc = 3;</code>
			 */
			public com.google.protobuf.ByteString getDescBytes() {
				java.lang.Object ref = desc_;
				if (ref instanceof String) {
					com.google.protobuf.ByteString b = com.google.protobuf.ByteString
							.copyFromUtf8((java.lang.String) ref);
					desc_ = b;
					return b;
				} else {
					return (com.google.protobuf.ByteString) ref;
				}
			}

			/**
			 * <code>required string desc = 3;</code>
			 */
			public Builder setDesc(java.lang.String value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000004;
				desc_ = value;
				onChanged();
				return this;
			}

			/**
			 * <code>required string desc = 3;</code>
			 */
			public Builder clearDesc() {
				bitField0_ = (bitField0_ & ~0x00000004);
				desc_ = getDefaultInstance().getDesc();
				onChanged();
				return this;
			}

			/**
			 * <code>required string desc = 3;</code>
			 */
			public Builder setDescBytes(com.google.protobuf.ByteString value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000004;
				desc_ = value;
				onChanged();
				return this;
			}

			public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
				return super.setUnknownFields(unknownFields);
			}

			public final Builder mergeUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
				return super.mergeUnknownFields(unknownFields);
			}

			// @@protoc_insertion_point(builder_scope:RespInfoProto)
		}

		// @@protoc_insertion_point(class_scope:RespInfoProto)
		private static final java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto DEFAULT_INSTANCE;
		static {
			DEFAULT_INSTANCE = new java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto();
		}

		public static java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto getDefaultInstance() {
			return DEFAULT_INSTANCE;
		}

		@java.lang.Deprecated
		public static final com.google.protobuf.Parser<RespInfoProto> PARSER = new com.google.protobuf.AbstractParser<RespInfoProto>() {
			public RespInfoProto parsePartialFrom(com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
					throws com.google.protobuf.InvalidProtocolBufferException {
				return new RespInfoProto(input, extensionRegistry);
			}
		};

		public static com.google.protobuf.Parser<RespInfoProto> parser() {
			return PARSER;
		}

		@java.lang.Override
		public com.google.protobuf.Parser<RespInfoProto> getParserForType() {
			return PARSER;
		}

		public java_spc.netty.serialize.protobuf.RespInfo.RespInfoProto getDefaultInstanceForType() {
			return DEFAULT_INSTANCE;
		}

	}

	private static final com.google.protobuf.Descriptors.Descriptor internal_static_RespInfoProto_descriptor;
	private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internal_static_RespInfoProto_fieldAccessorTable;

	public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
		return descriptor;
	}

	private static com.google.protobuf.Descriptors.FileDescriptor descriptor;
	static {
		java.lang.String[] descriptorData = {
				"\n;java_spc/netty/serialize/protobuf/prot" + "o/RespInfoProto.proto\"7\n\rRespInfoProto\022\n"
						+ "\n\002id\030\001 \002(\005\022\014\n\004code\030\002 \002(\005\022\014\n\004desc\030\003 \002(\tB-"
						+ "\n!java_spc.netty.serialize.protobufB\010Res" + "pInfo" };
		com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
			public com.google.protobuf.ExtensionRegistry assignDescriptors(
					com.google.protobuf.Descriptors.FileDescriptor root) {
				descriptor = root;
				return null;
			}
		};
		com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData,
				new com.google.protobuf.Descriptors.FileDescriptor[] {}, assigner);
		internal_static_RespInfoProto_descriptor = getDescriptor().getMessageTypes().get(0);
		internal_static_RespInfoProto_fieldAccessorTable = new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
				internal_static_RespInfoProto_descriptor, new java.lang.String[] { "Id", "Code", "Desc", });
	}

	// @@protoc_insertion_point(outer_class_scope)
}