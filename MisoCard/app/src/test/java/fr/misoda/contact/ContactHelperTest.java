package fr.misoda.contact;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

import fr.misoda.contact.common.ContactHelper;

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

    @Test
    public void getWorkPhone() {
        String text = "+1-555-2345";
        String phone = ContactHelper.getWorkPhone(text);
        Assert.assertEquals("", phone);

        text = "+358(0)1234567";
        phone = ContactHelper.getWorkPhone(text);
        Assert.assertEquals("+35801234567", phone);
    }

    @Test
    public void extractPhoneNumber() {
        String text = "+358(0)1234567";
        Set<String> numbers = ContactHelper.extractPhoneNumber(text, null);
        ArrayList<String> list = new ArrayList<>(numbers);
        //Assert.assertEquals(1, list.size());
        //Assert.assertEquals("+358(0)1234567", list.get(0));

        text = "Direct : 01 53 92 37 51";
        numbers = ContactHelper.extractPhoneNumber(text, "FR");
        list = new ArrayList<>(numbers);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("+33153923751", list.get(0));
    }

}