Apache Druid 0.23.0 contains over 450 new features, bug fixes, performance enhancements, documentation improvements, and additional test coverage from 81 contributors. [See the complete set of changes for additional details](https://github.com/apache/druid/milestone/45?closed=1).

# New Features

## Query engine

### Grouping on arrays without exploding the arrays
[12078](https://github.com/apache/druid/pull/12078)

### Specify a column other than __time column for row comparison in first/last aggregators
[11949](https://github.com/apache/druid/pull/11949)

## Streaming Ingestion

### Kafka Input format for parsing headers and key
[11630](https://github.com/apache/druid/pull/11630)

## Native Batch Ingestion

### Multi-dimensional range partitioning
Multi-dimensional range partitioning allows users to partition their data on the ranges of any number of dimensions. It develops further on the concepts behind "single-dim" partitioning and is now arguably the most preferable secondary partitioning both for query performance and storage efficiency.
[11848](https://github.com/apache/druid/pull/11848)
[11973](https://github.com/apache/druid/pull/11973)

## SQL

### Human-readable and actionable SQL error messages
Till 0.22.1, if a SQL query is not supported, users get a very cryptic and unhelpful error message. With this change, error message will include exactly what part of their SQL query is not supported by druid. One such example is executing a scan query that is ordered on a dimension other than time column. 
[11911](https://github.com/apache/druid/pull/11911)

### Cancel API for SQL queries
Users can now cancel SQL queries just like native queries can be cancelled. A new API has been added for cancelling SQL queries. Web-console now users this API to cancel SQL queries. Earlier, it only used to close the client connection while sql query keeps running in druid. 

[11643](https://github.com/apache/druid/pull/11643)
[11738](https://github.com/apache/druid/pull/11738)

## Coordinator/Overlord

## Web console

## Metrics

## Cloud integrations

## Other changes

# Security fixes

# Performance improvements

### General performance

### SQL

# Bug fixes
Druid 0.23.0 contains over 68 bug fixes. You can find the complete list [here](https://github.com/apache/druid/issues?q=milestone%3A0.23.0+label%3ABug) 

# Upgrading to 0.23.0

# Developer notices

# Known issues

For a full list of open issues, please see [Bug](https://github.com/apache/druid/labels/Bug) .

# Credits
Thanks to everyone who contributed to this release!

@2bethere
@317brian
@a2l007
@abhishekagarwal87
@adarshsanjeev
@aggarwalakshay
@AlexanderSaydakov
@AmatyaAvadhanula
@andreacyc
@ApoorvGuptaAi
@arunramani
@asdf2014
@AshishKapoor
@benkrug
@capistrant
@Caroline1000
@cheddar
@chenhuiyeh
@churromorales
@clintropolis
@cryptoe
@davidferlay
@dbardbar
@dependabot[bot]
@didip
@dkoepke
@dungdm93
@ektravel
@emirot
@FrankChen021
@gianm
@hqx871
@iMichka
@imply-cheddar
@isandeep41
@IvanVan
@jacobtolar
@jasonk000
@jgoz
@jihoonson
@jon-wei
@josephglanville
@joyking7
@kfaraz
@klarose
@LakshSingla
@liran-funaro
@lokesh-lingarajan
@loquisgon
@mark-imply
@maytasm
@mchades
@nikhil-ddu
@paul-rogers
@petermarshallio
@pjain1
@pjfanning
@rohangarg
@samarthjain
@sergioferragut
@shallada
@somu-imply
@sthetland
@suneet-s
@syacobovitz
@Tassatux
@techdocsmith
@tejaswini-imply
@themarcelor
@TSFenwick
@uschindler
@v-vishwa
@Vespira
@vogievetsky
@vtlim
@wangxiaobaidu11
@williamhyun
@wjhypo
@xvrl
@yuanlihan
@zachjsh