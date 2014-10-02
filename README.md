# scala-hipchat
is a scala library for HipChat api v2

*Sorry for my poor English. Please make a pull request if you found any error in the docs!*

## Why another scala hipchat library?
I want a "better" lib than [hipchat-scala](https://github.com/poweld/hipchat-scala)
+ "Better" design
    + scala-hipchat has internal struct similar to [ac-node-hipchat](https://bitbucket.org/atlassianlabs/ac-node-hipchat) and [ac-koa-hipchat](https://bitbucket.org/atlassianlabs/ac-koa-hipchat) - the offical nodejs libs from Atlassian.
    + Ex, both have:
        + Multitenancy support:
*One add-on can be installed with multiple HipChat OAuth2 clients, referred to here as 'tenants'. In practice, a tenant is either a HipChat room or group, depending on the installation scope of the add-on.*
        + class RestClient with all api method included.
        + class TenantClient that wrap RestClient & auto fetch-cache-reuse the appropriate token.
        + ... dig into code for others :D
+ Simpler for using
    + Just clone the source code, run `activator run` then you have a hipchat addon that can be install via the capabilities url http://localhost:9000/hipchat/capabilities
        + Update file `conf/capabilities.json` to change the addon domain name, urls. Ex `http://mydomain.com/hipchat/capabilities`
        + @see HipChat [addons](https://www.hipchat.com/docs/apiv2/addons) docs to understand Capabilities Descriptor.
        + @see [SslGuide.md] to setup & run the applicatino with https.
        + scala-hipchat use [Play Framework](https://www.playframework.com/documentation/2.3.x/Installing). Goto their docs for other help.
    + Just TenantClient.fromCache(tenantId) to get a Option[TenantClient] of the installed client. Then tenantClient.someApiMethod to invoke the hipchat api

## Dev guide
I use Intellij IDEA with scala plugin
### Setup H2 embed database to to store tenants data
+ Create data file ![Create data file](https://farm4.staticflickr.com/3930/15231240687_eb54c962f1_o.png)
+ Update db.default section in `conf/application.conf`

### Licence
This software is licensed under the Apache 2 license:
http://www.apache.org/licenses/LICENSE-2.0

Copyright 2014 Sân Đình (https://sandinh.com)
