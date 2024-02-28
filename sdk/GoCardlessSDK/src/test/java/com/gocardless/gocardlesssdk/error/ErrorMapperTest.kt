package com.gocardless.gocardlesssdk.error

import com.gocardless.gocardlesssdk.util.TestFileManager
import com.gocardless.gocardlesssdk.util.TestNetworkManager
import org.junit.Assert.assertEquals
import org.junit.Test

class ErrorMapperTest {
    private val errorMapper = ErrorMapper(TestNetworkManager.gson)

    @Test
    fun testAuthenticationException() {
        val reader = TestFileManager.reader("./billing_request/error.json")
        val exception = errorMapper.process(401, reader)
        assertEquals(AuthenticationException::class.java, exception::class.java)
    }

    @Test
    fun testPermissionException() {
        val reader = TestFileManager.reader("./billing_request/error.json")
        val exception = errorMapper.process(403, reader)
        assertEquals(PermissionException::class.java, exception::class.java)
    }

    @Test
    fun testRateLimitException() {
        val reader = TestFileManager.reader("./billing_request/error.json")
        val exception = errorMapper.process(429, reader)
        assertEquals(RateLimitException::class.java, exception::class.java)
    }

    @Test
    fun testMalformedResponseException() {
        val reader = TestFileManager.reader("./billing_request/error_malformed.json")
        val exception = errorMapper.process(500, reader)
        assertEquals(MalformedResponseException::class.java, exception::class.java)
    }
}