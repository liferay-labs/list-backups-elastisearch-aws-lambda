# AWS Lambda for listing Elasticsearch backups:

A parameter of type `ListBackupsRequest` must be provided to the Lambda.

```json
{
 "host": "endpoint of the aws elasticsearch domain",
 "bucket": "Snapshot repository name"
}
```

Reference:

[Elasticsearch cat api](https://www.elastic.co/guide/en/elasticsearch/reference/current/cat.html#cat)

[Elasticsearch cat snapshots](https://www.elastic.co/guide/en/elasticsearch/reference/current/cat-snapshots.html#cat-snapshots)