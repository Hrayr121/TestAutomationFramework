# Selenium + TestNG starter

Clone this repository as a starting point for UI automation. **Framework primitives** (driver lifecycle, config, listeners, utilities) live under `src/main/java/com/testframework/core`. **All page objects**—shared `BasePage` plus sample pages—live in one place: `src/main/java/com/testframework/pages`. **Example tests** live under `src/test/java/com/testframework/examples/tests`.

## Prerequisites

- JDK 23 (see `pom.xml`; change `<maven.compiler.source>` / `target` if your team standardizes on another LTS).
- Maven 3.9+.
- A browser installed locally (Chrome, Firefox, or Edge). WebDriverManager downloads matching drivers.

## Quick start

```bash
git clone <this-repo-url>
cd <repository-root>
mvn test
```

By default, `mvn test` runs `ExampleSiteSmokeTest` (loads `https://example.com`) so a fresh clone passes without a private app URL. The JSON-driven `SampleLoginTest` is **ignored** (`@Ignore`) until you point `base.url` at a real environment and remove that annotation.

## Configuration

| Location | Purpose |
|----------|---------|
| `src/main/resources/config.properties` | Defaults and `env=...` (which overlay file loads). |
| `src/main/resources/config-{dev,staging,prod}.properties` | Per-environment overrides (`base.url`, `browser`, `headless`, `timeout`, …). |

Override the active environment:

```bash
mvn test -Denv=staging
```

Useful keys (extend as needed): `browser`, `headless`, `base.url`, `timeout`, `env`.

## Where to add your work

1. **Tests** — Create a package such as `com.yourcompany.tests`, extend `com.testframework.core.BaseTest`, and add `@Test` methods.
2. **Page objects** — Add classes next to `BasePage` under `src/main/java/com/testframework/pages`, or use your own package (e.g. `com.yourcompany.pages`) extending `com.testframework.pages.BasePage`.
3. **Test data** — JSON under `src/test/resources/testdata/` (see `JsonReader.readAsDataProvider` in the example test).
4. **Suite** — Edit `testng.xml`: add `<package name="com.yourcompany.tests"/>` next to or instead of `com.testframework.examples.tests`.

Run a TestNG group:

```bash
mvn test -Dgroups=smoke
```

## Parallel runs

`testng.xml` uses `parallel="methods"`. `BaseTest` stores `WebDriver` in a `ThreadLocal` per thread. Increase `thread-count` only after your tests and app under test tolerate parallel sessions.

## Failure screenshots

`com.testframework.core.listeners.ScreenshotListener` writes PNGs under `target/screenshots/` on failure. Registered in `testng.xml`.

## CI

Run `mvn -B verify` in your pipeline. For headless agents, set `headless=true` in the appropriate `config-*.properties` or add a dedicated properties file and `-Denv=ci` once you add `config-ci.properties`.

## Graduating to a published JAR

If multiple repos need the same core:

1. Move `com.testframework.core` into a separate Maven module (e.g. `framework-core`) with this POM’s dependencies.
2. Depend on that module from your test projects with a normal `<dependency>` and semantic versions.

Until then, **treat this repo as the template**: fork or use “Use this template” on your host, then customize packages and config.

## Removing the examples

Delete `src/test/java/com/testframework/examples` and remove the `<package name="com.testframework.examples.tests"/>` entry from `testng.xml` once your own tests are in place. Remove or replace sample classes in `com.testframework.pages` (`MyAccountPage`, `DashboardPage`) when you no longer need them; keep `BasePage` as the parent for your pages unless you fork it.
