# AWS Lambda for listing Elasticsearch backups:

A parameter of type `ListBackupsRequest` must be provided to the Lambda.

```json
{
 "host": "endpoint of the aws elasticsearch domain",
 "bucket": "Snapshot repository name"
}
```

List AWS Elasticsearch DEV Backups

```json
{
  "host": "https://search-pulpo-elasticsearch-dev-2xp4jucrau2hcqsowbsaf5vnfu.us-east-1.es.amazonaws.com",
  "bucket": "us-east-1-pulpo-elasticsearch-dev-backup"
}
```

List AWS Elasticsearch PRE Backups (snapshots)

```json
{
  "host": "https://search-pulpo-elasticsearch-pre-sujappqca347putqnjmpm4h7l4.us-east-1.es.amazonaws.com",
  "bucket": "us-east-1-pulpo-elasticsearch-pre-backup"
}
```

List AWS Elasticsearch PROD Backups (snapshots)

```json
{
  "host": "https://search-pulpo-elasticsearch-prod-t7zkxvse3iizvi3i6rgckxv5ee.us-east-1.es.amazonaws.com/",
  "bucket": "us-east-1-pulpo-elasticsearch-prod-backup"
}
```

Reference:

[Elasticsearch cat api](https://www.elastic.co/guide/en/elasticsearch/reference/current/cat.html#cat)

[Easticsearch cat snapshots](https://www.elastic.co/guide/en/elasticsearch/reference/current/cat-snapshots.html#cat-snapshots)