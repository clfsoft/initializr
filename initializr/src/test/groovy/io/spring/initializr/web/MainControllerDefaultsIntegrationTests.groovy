package io.spring.initializr.web

import io.spring.initializr.support.PomAssert
import org.junit.Test

import org.springframework.test.context.ActiveProfiles

import static org.junit.Assert.assertTrue

/**
 * @author Stephane Nicoll
 */
@ActiveProfiles(['test-default', 'test-custom-defaults'])
class MainControllerDefaultsIntegrationTests extends AbstractMainControllerIntegrationTests {

	// see defaults customization

	@Test
	void generateDefaultPom() {
		String content = restTemplate.getForObject(createUrl('/pom.xml?style=web'), String)
		PomAssert pomAssert = new PomAssert(content)
		pomAssert.hasGroupId('org.foo').hasArtifactId('foo-bar').hasVersion('1.2.4-SNAPSHOT').hasPackaging('jar')
				.hasName('FooBar').hasDescription('FooBar Project').hasStartClass('org.foo.demo.Application')
	}

	@Test
	void defaultsAppliedToHome() {
		String body = htmlHome()
		assertTrue 'custom groupId not found', body.contains('org.foo')
		assertTrue 'custom artifactId not found', body.contains('foo-bar')
		assertTrue 'custom name not found', body.contains('FooBar')
		assertTrue 'custom description not found', body.contains('FooBar Project')
		assertTrue 'custom package not found', body.contains('org.foo.demo')
	}

}