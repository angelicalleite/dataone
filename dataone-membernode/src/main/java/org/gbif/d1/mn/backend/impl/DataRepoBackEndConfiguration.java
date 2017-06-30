package org.gbif.d1.mn.backend.impl;

import org.gbif.d1.mn.MNConfiguration;
import org.gbif.d1.mn.Tier;
import org.gbif.datarepo.conf.DataRepoConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * Configuration settings of a backend supported on the GBIF data repo.
 */
public class DataRepoBackendConfiguration extends MNConfiguration {

  /**
   * Configuration specific to interfacing with elastic search.
   */
  public static class ElasticSearch {


    public String host = "localhost";

    public int port = 9300;

    public String cluster = "content-cluster";

    public String idx = "logstash-datanone-*";

    @JsonProperty
    public String getHost() {
      return host;
    }

    public void setHost(String host) {
      this.host = host;
    }

    @JsonProperty
    public int getPort() {
      return port;
    }

    public void setPort(int port) {
      this.port = port;
    }

    @JsonProperty
    public String getCluster() {
      return cluster;
    }

    public void setCluster(String cluster) {
      this.cluster = cluster;
    }

    @JsonProperty
    public String getIdx() {
      return idx;
    }

    public void setIdx(String idx) {
      this.idx = idx;
    }

    /**
     * Creates a new instance of a ElasticSearch client.
     */
    public Client buildEsClient() {
      try {
        Settings settings = Settings.builder().put("cluster.name", cluster).build();
        return new PreBuiltTransportClient(settings).addTransportAddress(
          new InetSocketTransportAddress(InetAddress.getByName(host), port));
      } catch (UnknownHostException ex) {
        throw new IllegalStateException(ex);
      }
    }
  }

  private DataRepoConfiguration dataRepoConfiguration;

  private ElasticSearch elasticSearch;

  @JsonProperty
  public DataRepoConfiguration getDataRepoConfiguration() {
    return dataRepoConfiguration;
  }

  public void setDataRepoConfiguration(DataRepoConfiguration dataRepoConfiguration) {
    this.dataRepoConfiguration = dataRepoConfiguration;
  }

  @JsonProperty
  public ElasticSearch getElasticSearch() {
    return elasticSearch;
  }

  public void setElasticSearch(ElasticSearch elasticSearch) {
    this.elasticSearch = elasticSearch;
  }

  /**
   * DataOne implementation Tier.
   * This service implement up to tier 4.
   */
  public Tier getTier() {
    return Tier.TIER4;
  }
}
