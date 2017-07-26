// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: java_spc/netty/serialize/protobuf/proto/ReqInfoProto.proto

package java_spc.netty.serialize.protobuf;

public final class ReqInfo {
	private ReqInfo() {
	}

	public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {
	}

	public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
		registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
	}

	public interface ReqInfoProtoOrBuilder extends
			// @@protoc_insertion_point(interface_extends:ReqInfoProto)
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
		 * <code>required string uname = 2;</code>
		 */
		boolean hasUname();

		/**
		 * <code>required string uname = 2;</code>
		 */
		java.lang.String getUname();

		/**
		 * <code>required string uname = 2;</code>
		 */
		com.google.protobuf.ByteString getUnameBytes();

		/**
		 * <code>required string pname = 3;</code>
		 */
		boolean hasPname();

		/**
		 * <code>required string pname = 3;</code>
		 */
		java.lang.String getPname();

		/**
		 * <code>required string pname = 3;</code>
		 */
		com.google.protobuf.ByteString getPnameBytes();

		/**
		 * <code>required string address = 4;</code>
		 */
		boolean hasAddress();

		/**
		 * <code>required string address = 4;</code>
		 */
		java.lang.String getAddress();

		/**
		 * <code>required string address = 4;</code>
		 */
		com.google.protobuf.ByteString getAddressBytes();
	}

	/**
	 * Protobuf type {@code ReqInfoProto}
	 */
	public static final class ReqInfoProto extends com.google.protobuf.GeneratedMessageV3 implements
			// @@protoc_insertion_point(message_implements:ReqInfoProto)
			ReqInfoProtoOrBuilder {
		// Use ReqInfoProto.newBuilder() to construct.
		private ReqInfoProto(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
			super(builder);
		}

		private ReqInfoProto() {
			id_ = 0;
			uname_ = "";
			pname_ = "";
			address_ = "";
		}

		@java.lang.Override
		public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
			return this.unknownFields;
		}

		private ReqInfoProto(com.google.protobuf.CodedInputStream input,
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
					case 18: {
						com.google.protobuf.ByteString bs = input.readBytes();
						bitField0_ |= 0x00000002;
						uname_ = bs;
						break;
					}
					case 26: {
						com.google.protobuf.ByteString bs = input.readBytes();
						bitField0_ |= 0x00000004;
						pname_ = bs;
						break;
					}
					case 34: {
						com.google.protobuf.ByteString bs = input.readBytes();
						bitField0_ |= 0x00000008;
						address_ = bs;
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
			return java_spc.netty.serialize.protobuf.ReqInfo.internal_static_ReqInfoProto_descriptor;
		}

		protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
			return java_spc.netty.serialize.protobuf.ReqInfo.internal_static_ReqInfoProto_fieldAccessorTable
					.ensureFieldAccessorsInitialized(java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto.class,
							java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto.Builder.class);
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

		public static final int UNAME_FIELD_NUMBER = 2;
		private volatile java.lang.Object uname_;

		/**
		 * <code>required string uname = 2;</code>
		 */
		public boolean hasUname() {
			return ((bitField0_ & 0x00000002) == 0x00000002);
		}

		/**
		 * <code>required string uname = 2;</code>
		 */
		public java.lang.String getUname() {
			java.lang.Object ref = uname_;
			if (ref instanceof java.lang.String) {
				return (java.lang.String) ref;
			} else {
				com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
				java.lang.String s = bs.toStringUtf8();
				if (bs.isValidUtf8()) {
					uname_ = s;
				}
				return s;
			}
		}

		/**
		 * <code>required string uname = 2;</code>
		 */
		public com.google.protobuf.ByteString getUnameBytes() {
			java.lang.Object ref = uname_;
			if (ref instanceof java.lang.String) {
				com.google.protobuf.ByteString b = com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
				uname_ = b;
				return b;
			} else {
				return (com.google.protobuf.ByteString) ref;
			}
		}

		public static final int PNAME_FIELD_NUMBER = 3;
		private volatile java.lang.Object pname_;

		/**
		 * <code>required string pname = 3;</code>
		 */
		public boolean hasPname() {
			return ((bitField0_ & 0x00000004) == 0x00000004);
		}

		/**
		 * <code>required string pname = 3;</code>
		 */
		public java.lang.String getPname() {
			java.lang.Object ref = pname_;
			if (ref instanceof java.lang.String) {
				return (java.lang.String) ref;
			} else {
				com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
				java.lang.String s = bs.toStringUtf8();
				if (bs.isValidUtf8()) {
					pname_ = s;
				}
				return s;
			}
		}

		/**
		 * <code>required string pname = 3;</code>
		 */
		public com.google.protobuf.ByteString getPnameBytes() {
			java.lang.Object ref = pname_;
			if (ref instanceof java.lang.String) {
				com.google.protobuf.ByteString b = com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
				pname_ = b;
				return b;
			} else {
				return (com.google.protobuf.ByteString) ref;
			}
		}

		public static final int ADDRESS_FIELD_NUMBER = 4;
		private volatile java.lang.Object address_;

		/**
		 * <code>required string address = 4;</code>
		 */
		public boolean hasAddress() {
			return ((bitField0_ & 0x00000008) == 0x00000008);
		}

		/**
		 * <code>required string address = 4;</code>
		 */
		public java.lang.String getAddress() {
			java.lang.Object ref = address_;
			if (ref instanceof java.lang.String) {
				return (java.lang.String) ref;
			} else {
				com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
				java.lang.String s = bs.toStringUtf8();
				if (bs.isValidUtf8()) {
					address_ = s;
				}
				return s;
			}
		}

		/**
		 * <code>required string address = 4;</code>
		 */
		public com.google.protobuf.ByteString getAddressBytes() {
			java.lang.Object ref = address_;
			if (ref instanceof java.lang.String) {
				com.google.protobuf.ByteString b = com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
				address_ = b;
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
			if (!hasUname()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasPname()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasAddress()) {
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
				com.google.protobuf.GeneratedMessageV3.writeString(output, 2, uname_);
			}
			if (((bitField0_ & 0x00000004) == 0x00000004)) {
				com.google.protobuf.GeneratedMessageV3.writeString(output, 3, pname_);
			}
			if (((bitField0_ & 0x00000008) == 0x00000008)) {
				com.google.protobuf.GeneratedMessageV3.writeString(output, 4, address_);
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
				size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, uname_);
			}
			if (((bitField0_ & 0x00000004) == 0x00000004)) {
				size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, pname_);
			}
			if (((bitField0_ & 0x00000008) == 0x00000008)) {
				size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, address_);
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
			if (!(obj instanceof java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto)) {
				return super.equals(obj);
			}
			java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto other = (java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto) obj;

			boolean result = true;
			result = result && (hasId() == other.hasId());
			if (hasId()) {
				result = result && (getId() == other.getId());
			}
			result = result && (hasUname() == other.hasUname());
			if (hasUname()) {
				result = result && getUname().equals(other.getUname());
			}
			result = result && (hasPname() == other.hasPname());
			if (hasPname()) {
				result = result && getPname().equals(other.getPname());
			}
			result = result && (hasAddress() == other.hasAddress());
			if (hasAddress()) {
				result = result && getAddress().equals(other.getAddress());
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
			if (hasUname()) {
				hash = (37 * hash) + UNAME_FIELD_NUMBER;
				hash = (53 * hash) + getUname().hashCode();
			}
			if (hasPname()) {
				hash = (37 * hash) + PNAME_FIELD_NUMBER;
				hash = (53 * hash) + getPname().hashCode();
			}
			if (hasAddress()) {
				hash = (37 * hash) + ADDRESS_FIELD_NUMBER;
				hash = (53 * hash) + getAddress().hashCode();
			}
			hash = (29 * hash) + unknownFields.hashCode();
			memoizedHashCode = hash;
			return hash;
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parseFrom(java.nio.ByteBuffer data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parseFrom(java.nio.ByteBuffer data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parseFrom(
				com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parseFrom(
				com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parseFrom(byte[] data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parseFrom(byte[] data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parseFrom(java.io.InputStream input)
				throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parseFrom(java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parseDelimitedFrom(
				java.io.InputStream input) throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parseDelimitedFrom(
				java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input,
					extensionRegistry);
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parseFrom(
				com.google.protobuf.CodedInputStream input) throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parseFrom(
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

		public static Builder newBuilder(java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto prototype) {
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
		 * Protobuf type {@code ReqInfoProto}
		 */
		public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
				// @@protoc_insertion_point(builder_implements:ReqInfoProto)
				java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProtoOrBuilder {
			public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
				return java_spc.netty.serialize.protobuf.ReqInfo.internal_static_ReqInfoProto_descriptor;
			}

			protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
				return java_spc.netty.serialize.protobuf.ReqInfo.internal_static_ReqInfoProto_fieldAccessorTable
						.ensureFieldAccessorsInitialized(java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto.class,
								java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto.Builder.class);
			}

			// Construct using
			// java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto.newBuilder()
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
				uname_ = "";
				bitField0_ = (bitField0_ & ~0x00000002);
				pname_ = "";
				bitField0_ = (bitField0_ & ~0x00000004);
				address_ = "";
				bitField0_ = (bitField0_ & ~0x00000008);
				return this;
			}

			public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
				return java_spc.netty.serialize.protobuf.ReqInfo.internal_static_ReqInfoProto_descriptor;
			}

			public java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto getDefaultInstanceForType() {
				return java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto.getDefaultInstance();
			}

			public java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto build() {
				java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto result = buildPartial();
				if (!result.isInitialized()) {
					throw newUninitializedMessageException(result);
				}
				return result;
			}

			public java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto buildPartial() {
				java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto result = new java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto(
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
				result.uname_ = uname_;
				if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
					to_bitField0_ |= 0x00000004;
				}
				result.pname_ = pname_;
				if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
					to_bitField0_ |= 0x00000008;
				}
				result.address_ = address_;
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
				if (other instanceof java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto) {
					return mergeFrom((java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto) other);
				} else {
					super.mergeFrom(other);
					return this;
				}
			}

			public Builder mergeFrom(java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto other) {
				if (other == java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto.getDefaultInstance())
					return this;
				if (other.hasId()) {
					setId(other.getId());
				}
				if (other.hasUname()) {
					bitField0_ |= 0x00000002;
					uname_ = other.uname_;
					onChanged();
				}
				if (other.hasPname()) {
					bitField0_ |= 0x00000004;
					pname_ = other.pname_;
					onChanged();
				}
				if (other.hasAddress()) {
					bitField0_ |= 0x00000008;
					address_ = other.address_;
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
				if (!hasUname()) {
					return false;
				}
				if (!hasPname()) {
					return false;
				}
				if (!hasAddress()) {
					return false;
				}
				return true;
			}

			public Builder mergeFrom(com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
				java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto parsedMessage = null;
				try {
					parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
				} catch (com.google.protobuf.InvalidProtocolBufferException e) {
					parsedMessage = (java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto) e.getUnfinishedMessage();
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

			private java.lang.Object uname_ = "";

			/**
			 * <code>required string uname = 2;</code>
			 */
			public boolean hasUname() {
				return ((bitField0_ & 0x00000002) == 0x00000002);
			}

			/**
			 * <code>required string uname = 2;</code>
			 */
			public java.lang.String getUname() {
				java.lang.Object ref = uname_;
				if (!(ref instanceof java.lang.String)) {
					com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
					java.lang.String s = bs.toStringUtf8();
					if (bs.isValidUtf8()) {
						uname_ = s;
					}
					return s;
				} else {
					return (java.lang.String) ref;
				}
			}

			/**
			 * <code>required string uname = 2;</code>
			 */
			public com.google.protobuf.ByteString getUnameBytes() {
				java.lang.Object ref = uname_;
				if (ref instanceof String) {
					com.google.protobuf.ByteString b = com.google.protobuf.ByteString
							.copyFromUtf8((java.lang.String) ref);
					uname_ = b;
					return b;
				} else {
					return (com.google.protobuf.ByteString) ref;
				}
			}

			/**
			 * <code>required string uname = 2;</code>
			 */
			public Builder setUname(java.lang.String value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000002;
				uname_ = value;
				onChanged();
				return this;
			}

			/**
			 * <code>required string uname = 2;</code>
			 */
			public Builder clearUname() {
				bitField0_ = (bitField0_ & ~0x00000002);
				uname_ = getDefaultInstance().getUname();
				onChanged();
				return this;
			}

			/**
			 * <code>required string uname = 2;</code>
			 */
			public Builder setUnameBytes(com.google.protobuf.ByteString value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000002;
				uname_ = value;
				onChanged();
				return this;
			}

			private java.lang.Object pname_ = "";

			/**
			 * <code>required string pname = 3;</code>
			 */
			public boolean hasPname() {
				return ((bitField0_ & 0x00000004) == 0x00000004);
			}

			/**
			 * <code>required string pname = 3;</code>
			 */
			public java.lang.String getPname() {
				java.lang.Object ref = pname_;
				if (!(ref instanceof java.lang.String)) {
					com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
					java.lang.String s = bs.toStringUtf8();
					if (bs.isValidUtf8()) {
						pname_ = s;
					}
					return s;
				} else {
					return (java.lang.String) ref;
				}
			}

			/**
			 * <code>required string pname = 3;</code>
			 */
			public com.google.protobuf.ByteString getPnameBytes() {
				java.lang.Object ref = pname_;
				if (ref instanceof String) {
					com.google.protobuf.ByteString b = com.google.protobuf.ByteString
							.copyFromUtf8((java.lang.String) ref);
					pname_ = b;
					return b;
				} else {
					return (com.google.protobuf.ByteString) ref;
				}
			}

			/**
			 * <code>required string pname = 3;</code>
			 */
			public Builder setPname(java.lang.String value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000004;
				pname_ = value;
				onChanged();
				return this;
			}

			/**
			 * <code>required string pname = 3;</code>
			 */
			public Builder clearPname() {
				bitField0_ = (bitField0_ & ~0x00000004);
				pname_ = getDefaultInstance().getPname();
				onChanged();
				return this;
			}

			/**
			 * <code>required string pname = 3;</code>
			 */
			public Builder setPnameBytes(com.google.protobuf.ByteString value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000004;
				pname_ = value;
				onChanged();
				return this;
			}

			private java.lang.Object address_ = "";

			/**
			 * <code>required string address = 4;</code>
			 */
			public boolean hasAddress() {
				return ((bitField0_ & 0x00000008) == 0x00000008);
			}

			/**
			 * <code>required string address = 4;</code>
			 */
			public java.lang.String getAddress() {
				java.lang.Object ref = address_;
				if (!(ref instanceof java.lang.String)) {
					com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
					java.lang.String s = bs.toStringUtf8();
					if (bs.isValidUtf8()) {
						address_ = s;
					}
					return s;
				} else {
					return (java.lang.String) ref;
				}
			}

			/**
			 * <code>required string address = 4;</code>
			 */
			public com.google.protobuf.ByteString getAddressBytes() {
				java.lang.Object ref = address_;
				if (ref instanceof String) {
					com.google.protobuf.ByteString b = com.google.protobuf.ByteString
							.copyFromUtf8((java.lang.String) ref);
					address_ = b;
					return b;
				} else {
					return (com.google.protobuf.ByteString) ref;
				}
			}

			/**
			 * <code>required string address = 4;</code>
			 */
			public Builder setAddress(java.lang.String value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000008;
				address_ = value;
				onChanged();
				return this;
			}

			/**
			 * <code>required string address = 4;</code>
			 */
			public Builder clearAddress() {
				bitField0_ = (bitField0_ & ~0x00000008);
				address_ = getDefaultInstance().getAddress();
				onChanged();
				return this;
			}

			/**
			 * <code>required string address = 4;</code>
			 */
			public Builder setAddressBytes(com.google.protobuf.ByteString value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000008;
				address_ = value;
				onChanged();
				return this;
			}

			public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
				return super.setUnknownFields(unknownFields);
			}

			public final Builder mergeUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
				return super.mergeUnknownFields(unknownFields);
			}

			// @@protoc_insertion_point(builder_scope:ReqInfoProto)
		}

		// @@protoc_insertion_point(class_scope:ReqInfoProto)
		private static final java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto DEFAULT_INSTANCE;
		static {
			DEFAULT_INSTANCE = new java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto();
		}

		public static java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto getDefaultInstance() {
			return DEFAULT_INSTANCE;
		}

		@java.lang.Deprecated
		public static final com.google.protobuf.Parser<ReqInfoProto> PARSER = new com.google.protobuf.AbstractParser<ReqInfoProto>() {
			public ReqInfoProto parsePartialFrom(com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
					throws com.google.protobuf.InvalidProtocolBufferException {
				return new ReqInfoProto(input, extensionRegistry);
			}
		};

		public static com.google.protobuf.Parser<ReqInfoProto> parser() {
			return PARSER;
		}

		@java.lang.Override
		public com.google.protobuf.Parser<ReqInfoProto> getParserForType() {
			return PARSER;
		}

		public java_spc.netty.serialize.protobuf.ReqInfo.ReqInfoProto getDefaultInstanceForType() {
			return DEFAULT_INSTANCE;
		}

	}

	private static final com.google.protobuf.Descriptors.Descriptor internal_static_ReqInfoProto_descriptor;
	private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internal_static_ReqInfoProto_fieldAccessorTable;

	public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
		return descriptor;
	}

	private static com.google.protobuf.Descriptors.FileDescriptor descriptor;
	static {
		java.lang.String[] descriptorData = {
				"\n:java_spc/netty/serialize/protobuf/prot" + "o/ReqInfoProto.proto\"I\n\014ReqInfoProto\022\n\n\002"
						+ "id\030\001 \002(\005\022\r\n\005uname\030\002 \002(\t\022\r\n\005pname\030\003 \002(\t\022\017"
						+ "\n\007address\030\004 \002(\tB,\n!java_spc.netty.serial" + "ize.protobufB\007ReqInfo" };
		com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
			public com.google.protobuf.ExtensionRegistry assignDescriptors(
					com.google.protobuf.Descriptors.FileDescriptor root) {
				descriptor = root;
				return null;
			}
		};
		com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData,
				new com.google.protobuf.Descriptors.FileDescriptor[] {}, assigner);
		internal_static_ReqInfoProto_descriptor = getDescriptor().getMessageTypes().get(0);
		internal_static_ReqInfoProto_fieldAccessorTable = new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
				internal_static_ReqInfoProto_descriptor, new java.lang.String[] { "Id", "Uname", "Pname", "Address", });
	}

	// @@protoc_insertion_point(outer_class_scope)
}
