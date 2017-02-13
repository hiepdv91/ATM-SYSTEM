package TableSearch;

import Guide.Management;
import java.util.regex.Pattern;
import javax.swing.RowFilter;
import org.jdesktop.beans.AbstractBean;
import org.jdesktop.swingx.JXTable;

public class MyTableFilter extends AbstractBean {

    private RowFilter<Object, Object> searchFilter;
    private String filterString;
    private JXTable table;

    public MyTableFilter(JXTable table) {
        this.table = table;
    }

    public boolean isFilteringByString() {
        return !isEmpty(getFilterString());
    }

    private boolean isEmpty(String filterString) {
        return filterString == null || filterString.length() == 0;
    }

    public void setFilterString(String filterString) {
        String oldValue = getFilterString();
        // <snip> Filter control 
        // set the filter string (bound to the input in the textfield) 
        // and update the search RowFilter 
        this.filterString = filterString;
        updateSearchFilter();
        //        </snip> 
        firePropertyChange("filterString", oldValue, getFilterString());
    }

    /**
     * @return the filterString
     */
    public String getFilterString() {
        return filterString;
    }

    private void updateSearchFilter() {
        if ((filterString != null) && (filterString.length() > 0)) {
            searchFilter = createSearchFilter(filterString + ".*");
        } else {
            searchFilter = null;
        }
        updateFilters();
    }

    private void updateFilters() {
        // <snip> Filter control 
        // set the filters to the table   
        //if (searchFilter != null) {
        table.setRowFilter(searchFilter);
        //}
        //        </snip> 
    }

    private RowFilter<Object, Object> createSearchFilter(final String filterString) {
        return new RowFilter<Object, Object>() {

            @Override
            public boolean include(RowFilter.Entry<? extends Object, ? extends Object> entry) {
                Pattern p = Pattern.compile(filterString + ".*", Pattern.CASE_INSENSITIVE);
                String value1 = entry.getValue(0).toString();
                String value2 = entry.getValue(1).toString();
                String value3 = entry.getValue(2).toString();

                boolean matches1 = false;
                boolean matches2 = false;
                boolean matches3 = false;

                // Returning true indicates this row should be shown. 
                matches1 = p.matcher(value1).matches();
                matches2 = p.matcher(value2).matches();
                matches3 = p.matcher(value3).matches();
                
                return matches1 || matches2 || matches3;

                //                </snip> 
            }
        };
    }
}
