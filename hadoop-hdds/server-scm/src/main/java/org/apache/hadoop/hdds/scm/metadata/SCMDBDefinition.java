/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hdds.scm.metadata;

import java.math.BigInteger;
import java.security.cert.X509Certificate;

import org.apache.hadoop.hdds.protocol.proto.StorageContainerDatanodeProtocolProtos.DeletedBlocksTransaction;
import org.apache.hadoop.hdds.scm.ScmConfigKeys;
import org.apache.hadoop.hdds.scm.container.ContainerID;
import org.apache.hadoop.hdds.scm.container.ContainerInfo;
import org.apache.hadoop.hdds.scm.pipeline.Pipeline;
import org.apache.hadoop.hdds.scm.pipeline.PipelineID;
import org.apache.hadoop.hdds.utils.db.DBColumnFamilyDefinition;
import org.apache.hadoop.hdds.utils.db.DBDefinition;
import org.apache.hadoop.hdds.utils.db.LongCodec;

/**
 * Class defines the structure and types of the scm.db.
 */
public class SCMDBDefinition implements DBDefinition { // 表结构定义

  public static final DBColumnFamilyDefinition<Long, DeletedBlocksTransaction>
      DELETED_BLOCKS =
      new DBColumnFamilyDefinition<>(
          "deletedBlocks",
          Long.class,
          new LongCodec(),
          DeletedBlocksTransaction.class,
          new DeletedBlocksTransactionCodec());

  public static final DBColumnFamilyDefinition<BigInteger, X509Certificate>
      VALID_CERTS =
      new DBColumnFamilyDefinition<>(
          "validCerts",
          BigInteger.class,
          new BigIntegerCodec(),
          X509Certificate.class,
          new X509CertificateCodec());

  public static final DBColumnFamilyDefinition<BigInteger, X509Certificate>
      REVOKED_CERTS =
      new DBColumnFamilyDefinition<>(
          "revokedCerts",
          BigInteger.class,
          new BigIntegerCodec(),
          X509Certificate.class,
          new X509CertificateCodec());

  public static final DBColumnFamilyDefinition<PipelineID, Pipeline>
      PIPELINES =
      new DBColumnFamilyDefinition<>(
          "pipelines",
          PipelineID.class,
          new PipelineIDCodec(),
          Pipeline.class,
          new PipelineCodec());

  public static final DBColumnFamilyDefinition<ContainerID, ContainerInfo>
      CONTAINERS =
      new DBColumnFamilyDefinition<ContainerID, ContainerInfo>(
          "containers",
          ContainerID.class,
          new ContainerIDCodec(),
          ContainerInfo.class,
          new ContainerInfoCodec());

  @Override
  public String getName() {
    return "scm.db";
  }

  @Override
  public String getLocationConfigKey() {
    return ScmConfigKeys.OZONE_SCM_DB_DIRS;
  }

  @Override
  public DBColumnFamilyDefinition[] getColumnFamilies() {
    return new DBColumnFamilyDefinition[] {DELETED_BLOCKS, VALID_CERTS,
        REVOKED_CERTS, PIPELINES, CONTAINERS};
  }
}
