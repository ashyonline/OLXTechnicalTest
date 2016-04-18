package codingbad.com.olxtechnicaltest.view;

/**
 * Created by Ayelen Chavez on 4/18/16.
 */
public class MainActivity extends AbstractAppCompatActivity {

    @Override
    protected void setInitialFragment() {
        setInitialFragment(MainFragment.newInstance());
    }
}
