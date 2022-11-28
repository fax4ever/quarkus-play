1. Run OpenSearch

``` bash
docker run --name opensearch -e "discovery.type=single-node" -e "plugins.security.ssl.http.enabled=false" \
    --rm -p 9200:9200 opensearchproject/opensearch:2.4.0
```

2. Build Quarkus client application

> mvn clean install -DskipTests

3. Run Quarkus client application (in dev mode)

> mvn quarkus:dev -Ddebug

4. See the logs:

``` bash
2022-11-28 08:15:10,952 INFO  [fax.pla.FruitActivator] (Quarkus Main Thread) config index --> {"acknowledged":true,"shards_acknowledged":true,"index":"fruit"}
2022-11-28 08:15:11,112 INFO  [fax.pla.FruitActivator] (Quarkus Main Thread) index 1 --> {"_index":"fruit","_id":"25161423-9546-4f66-92f2-dee22615e288","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":0,"_primary_term":1}
2022-11-28 08:15:11,127 INFO  [fax.pla.FruitActivator] (Quarkus Main Thread) index 2 --> {"_index":"fruit","_id":"8c63badc-14e1-495f-9a61-324c142bda30","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":1,"_primary_term":1}
2022-11-28 08:15:12,209 INFO  [fax.pla.FruitActivator] (Quarkus Main Thread) reds --> {"took":63,"timed_out":false,"_shards":{"total":1,"successful":1,"skipped":0,"failed":0},"hits":{"total":{"value":1,"relation":"eq"},"max_score":0.6931471,"hits":[{"_index":"fruit","_id":"25161423-9546-4f66-92f2-dee22615e288","_score":0.6931471}]}}
```

5. If you restart only the client app, you will see the logs:

``` bash
2022-11-28 08:16:40,596 INFO  [fax.pla.FruitActivator] (Quarkus Main Thread) config index --> method [PUT], host [http://localhost:9200], URI [/fruit], status line [HTTP/1.1 400 Bad Request]
{"error":{"root_cause":[{"type":"resource_already_exists_exception","reason":"index [fruit/2-uwbMVcTWKYL3xkPRnH0Q] already exists","index":"fruit","index_uuid":"2-uwbMVcTWKYL3xkPRnH0Q"}],"type":"resource_already_exists_exception","reason":"index [fruit/2-uwbMVcTWKYL3xkPRnH0Q] already exists","index":"fruit","index_uuid":"2-uwbMVcTWKYL3xkPRnH0Q"},"status":400}
2022-11-28 08:16:40,624 INFO  [fax.pla.FruitActivator] (Quarkus Main Thread) index 1 --> {"_index":"fruit","_id":"c9122612-8da0-4f43-b259-49eb47f61b9b","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":2,"_primary_term":1}
2022-11-28 08:16:40,636 INFO  [fax.pla.FruitActivator] (Quarkus Main Thread) index 2 --> {"_index":"fruit","_id":"3011498c-b956-476a-92f5-f7d5f830a064","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":3,"_primary_term":1}
2022-11-28 08:16:42,122 INFO  [fax.pla.FruitActivator] (Quarkus Main Thread) reds --> {"took":479,"timed_out":false,"_shards":{"total":1,"successful":1,"skipped":0,"failed":0},"hits":{"total":{"value":2,"relation":"eq"},"max_score":0.6931471,"hits":[{"_index":"fruit","_id":"25161423-9546-4f66-92f2-dee22615e288","_score":0.6931471},{"_index":"fruit","_id":"c9122612-8da0-4f43-b259-49eb47f61b9b","_score":0.6931471}]}}
```