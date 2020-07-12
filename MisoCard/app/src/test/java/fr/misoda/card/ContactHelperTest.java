package fr.misoda.card;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import fr.misoda.card.common.ContactHelper;

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

    @Test
    public void getName() {
        String text = "kjdkfjd@gmail.com \n";
        String name = ContactHelper.getName(text);
        Assert.assertEquals(StringUtils.EMPTY, name);

        text = "Laura\nlaura@gmail.com \nMobile: 06 66 66 66 66";
        name = ContactHelper.getName(text);
        Assert.assertEquals("Laura", name);

        text = "Laura \nlaura@gmail.com \nMobile: 06 66 66 66 66";
        name = ContactHelper.getName(text);
        Assert.assertEquals("Laura ", name);

        text = "Laura Anna\nlaura@gmail.com \nMobile: 06 66 66 66 66";
        name = ContactHelper.getName(text);
        Assert.assertEquals("Laura Anna", name);
    }
}