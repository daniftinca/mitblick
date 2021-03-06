package boundary;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import filter.AuthenticationFilter;
import filter.CorsFilter;
import notification.boundary.NotificationManagementBoundary;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import profile.boundary.PdfGeneratorBoundary;
import profile.boundary.ProfileManagementBoundary;
import projekt.boundary.ProjektManagementBoundary;
import skills.boundary.SkillAreaManagementBoundary;
import skills.boundary.SkillManagementBoundary;
import user.boundary.AuthenticationBoundary;
import user.boundary.EmployeeManagementBoundary;
import user.boundary.PermissionManagementBoundary;
import user.boundary.UserManagementBoundary;

import javax.servlet.Registration;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest/")
public class AppBoundary extends Application {

    /*Some test comment
     * Another test comment*/
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(JacksonJaxbJsonProvider.class);
        classes.add(AuthenticationBoundary.class);
        classes.add(AuthenticationFilter.class);
        classes.add(Registration.class);
        classes.add(UserManagementBoundary.class);
        classes.add(PermissionManagementBoundary.class);
        classes.add(ProfileManagementBoundary.class);
        classes.add(PdfGeneratorBoundary.class);
        classes.add(ProjektManagementBoundary.class);
        classes.add(CorsFilter.class);
        classes.add(MultiPartFeature.class);
        classes.add(SkillManagementBoundary.class);
        classes.add(SkillAreaManagementBoundary.class);
        classes.add(EmployeeManagementBoundary.class);
        classes.add(NotificationManagementBoundary.class);

        return classes;
    }
}
