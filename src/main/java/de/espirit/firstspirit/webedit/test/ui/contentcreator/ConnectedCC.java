package de.espirit.firstspirit.webedit.test.ui.contentcreator;

import de.espirit.firstspirit.access.project.Project;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;



/**
 * Extension of the {@link SimplyCC WebEdit-UI-adapter}.
 */
public class ConnectedCC extends SimplyCC {
    
    private static final Logger LOGGER = Logger.getLogger(ConnectedCC.class);
    
    private final Project project;
    
    
    public ConnectedCC(final Project project, final WebDriver driver, final String url, final String ssoTicket) {
        super(driver, url + "&login.ticket=" + ssoTicket);
        this.project = project;
    }
    
    
    public ConnectedCC(final Project project, final WebDriver driver, final String webEditUrl) {
        super(driver, webEditUrl);
        this.project = project;
    }
    
    
    public Project project() {
        return project;
    }
}
