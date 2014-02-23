# Dropwizard Curator

## Integrating [Apache Curator](http://curator.apache.org/) with the [Dropwizard](http://www.dropwizard.io/) lifecycle.

### Dependency Info
```
<dependency>
    <groupId>com.robertcboll.dropwizard</groupId>
    <artifactId>dropwizard-curator</artifactId>
    <version>0.1</version>
</dependency>
```
Currently only available through a private maven repository:
```
<repository>
    <id>robertcboll-nexus-public</id>
    <name>robertcboll public</name>
    <url>http://nexus.robertcboll.com/nexus/content/groups/public/</url>
    <releases><enabled>true</enabled></releases>
    <snapshots><enabled>false</enabled></snapshots>
</repository>
```

### Basic Usage

Make your application's configuration inherit from `CuratorConfiguration`.
```java

public class AppConfiguration extends Configuration implements CuratorConfiguration
```

Update your application's configuration file to include your curator configuration.
```yaml

curator:
  curators:
  - name: "local"
    connectionString: "localhost:2181"
```

In your application's `initialize` method, register a new `CuratorBundle` with the `bootstrap`.
```java

bootstrap.addBundle(new CuratorBundle<>());
```

Your curator is accessible via `Curators`.
```java

// throws an exception of no curator with that name is registered
CuratorFramework curator = Curators.named("local");
```

You're all set! Check your application's health checks to verify the connection(s).

For a working example, check out the [example](example/src/main/java/com/robertcboll/dropwizard/curator/example/basic).

### Service Discovery

Advertised services must extend `DiscoverableService`.
```java

public class FooService extends DiscoverableService
```

#### Manual Service Advertising

Make your Applications configuration inherit from `CuratorConfiguration`.
```java

public class AppConfiguration extends Configuration implements CuratorConfiguration
```

Update your application's configuration file to include your curator configuration.
```yaml

curator:
  curators:
  - name: "local"
    connectionString: "localhost:2181"
```

In your application's `initialize` method, register a new `SimpleDiscoveryBundle` with the `bootstrap`, passing in the services you wish to advertise.
```java

bootstrap.addBundle(new SimpleDiscoveryBundle(ImmutableList.<DiscoverableService>of(new FooService())));
// or
bootstrap.addBundle(new SimpleDiscoveryBundle(ImmutableList.of(new FooService(), new BarService())));
```

You're all set! Check your zookeeper to see your advertised service, and check your application's health checks to verify the connections.


For a working example, check out the [example](example/src/main/java/com/robertcboll/dropwizard/curator/example/discovery/simple).

#### Service Advertising via Configuration

Make your application's configuration inherit from `DiscoveryConfiguration<FooService>`.
```java

public class AppConfiguration extends Configuration implements DiscoveryConfiguration<FooService>
```

Update your application's configuration file to include your curator configuration, and your discovery configuration.
```yaml

curator:
  curators:
  - name: "local"
    connectionString: "localhost:2181"

service:
  description: "A sample discoverable service."
```

In your application's `initialize` method, register a new `ConfiguredDiscoveryBundle` with the `bootstrap`. This will advertise to all available curators.
Want to advertise to a select curator? Send a PR!
```java

bootstrap.addBundle(new ConfiguredDiscoveryBundle<FooService>());
```

You're all set! Check your zookeeper to see your advertised service, and check your application's health checks to verify the connections.

For a working example, check out the [example](example/src/main/java/com/robertcboll/dropwizard/curator/example/discovery/configured).

#### Discovering Services

Assuming you have curator configured in your applications `initialize` method...

Create a ServiceProvider in your application's `run` method.
```java

ServiceProvider<BarService> barProvider = ManagedServiceProvider.create(BarService.class, new BarService(), environment, Curators.named("default"));
// or
Collection<ServiceProvider<BarService>> barProviders = ManagedServiceProvider.create(BarService.class, new BarService(), environment);
```
Note: The former will return a provider from the specified curator instance. The later will return a provider from each available curator.
Suggestions for improvement here are welcome. Open a ticket or send a PR.

For a working example, check out the [example](example/src/main/java/com/robertcboll/dropwizard/curator/example/discovery/simple).
