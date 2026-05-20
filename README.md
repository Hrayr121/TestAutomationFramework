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

## Allure reports

Allure records **every** test execution — **passed**, **failed**, and **skipped** — under `target/allure-results/` during `mvn test`. The HTML report shows all of them; failures additionally get screenshot, URL, and stack trace attachments from `ScreenshotListener`.

Listeners in `testng.xml`:

| Listener | Role |
|----------|------|
| `io.qameta.allure.testng.AllureTestNg` | Allure ↔ TestNG lifecycle |
| `AllureEnvironmentListener` | Labels/parameters: `env`, `browser`, `headless`, `base.url` |
| `ScreenshotListener` | On failure: PNG to `target/screenshots/` + Allure attachments (screenshot, URL, stack trace) |

**View the HTML report locally:**

```bash
mvn test
mvn allure:serve
```

`allure:serve` opens a browser with the report (requires a display). To generate static HTML without serving:

```bash
mvn allure:report
```

Output is under `target/site/allure-maven-plugin/` (open `index.html`).

**CI:** Run `mvn test`, then publish `target/allure-results/` (and optionally `target/screenshots/`) as build artifacts. Generate the report in the pipeline with `mvn allure:report` if agents should not use `allure:serve`.

Surefire JUnit XML in `target/surefire-reports/` remains available for pass/fail gates.

**Demo failures for Allure** (one passing smoke + three intentional failures; Maven build will fail):

```bash
mvn test -Dsurefire.suiteXmlFiles=testng-failure-demo.xml
mvn allure:serve
```

Failure demos live in package `com.testframework.examples.failuredemos` (not scanned by default `testng.xml`, which only includes `com.testframework.examples.tests`).

## CI (GitHub Actions)

Workflow: [`.github/workflows/ci.yml`](.github/workflows/ci.yml)

| Trigger | Behavior |
|---------|----------|
| Push / PR to `main` or `master` | `mvn clean test -Denv=ci` (headless Chrome) |
| Nightly (`02:00 UTC`) | Same suite |
| Manual | **Actions** → **CI** → **Run workflow** |

CI uses **`config-ci.properties`** (`headless=true`, public demo URLs). It does **not** use your corporate `settings.xml`; dependencies come from Maven Central on the runner.

**Artifacts** (14 days): Surefire reports, `allure-results`, generated Allure HTML, failure screenshots — download from the workflow run → **Artifacts**.

**Slack on failure (optional):** In the repo → **Settings** → **Secrets and variables** → **Actions** → New secret `SLACK_WEBHOOK_URL` (Incoming Webhook URL). If unset, tests still run; only the notify step is skipped.

**Local parity with CI:**

```bash
mvn clean test -Denv=ci
```

Other CI systems (Jenkins, GitLab): same command plus upload `target/allure-results/` and `target/surefire-reports/`.

## Graduating to a published JAR

If multiple repos need the same core:

1. Move `com.testframework.core` into a separate Maven module (e.g. `framework-core`) with this POM’s dependencies.
2. Depend on that module from your test projects with a normal `<dependency>` and semantic versions.

Until then, **treat this repo as the template**: fork or use “Use this template” on your host, then customize packages and config.

## Removing the examples

Delete `src/test/java/com/testframework/examples` and remove the `<package name="com.testframework.examples.tests"/>` entry from `testng.xml` once your own tests are in place. Remove or replace sample classes in `com.testframework.pages` (`LoginPage`, `DashboardPage`) when you no longer need them; keep `BasePage` as the parent for your pages unless you fork it.
