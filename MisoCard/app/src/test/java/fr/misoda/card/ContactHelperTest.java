package fr.misoda.card;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactHelperTest {

    @Test
    public void getEmail() {
        String text = "kjdkfjd";
        String email = ContactHelper.getEmail(text);
        Assert.assertEquals(StringUtils.EMPTY, email);

        text = "email : ffis@gmail.com";
        email = ContactHelper.getEmail(text);
        Assert.assertEquals("ffis@gmail.com", email);

        text = "email\nffis@gmail.com";
        email = ContactHelper.getEmail(text);
        Assert.assertEquals("ffis@gmail.com", email);
    }
}