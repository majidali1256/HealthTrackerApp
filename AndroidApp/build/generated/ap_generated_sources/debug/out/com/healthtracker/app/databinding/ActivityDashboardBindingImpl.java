package com.healthtracker.app.databinding;
import com.healthtracker.app.R;
import com.healthtracker.app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityDashboardBindingImpl extends ActivityDashboardBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.headerSection, 1);
        sViewsWithIds.put(R.id.tvGreeting, 2);
        sViewsWithIds.put(R.id.tvUserName, 3);
        sViewsWithIds.put(R.id.cardProfile, 4);
        sViewsWithIds.put(R.id.ivProfile, 5);
        sViewsWithIds.put(R.id.cardPrimaryMetric, 6);
        sViewsWithIds.put(R.id.tvPrimaryMetricLabel, 7);
        sViewsWithIds.put(R.id.tvPrimaryMetricValue, 8);
        sViewsWithIds.put(R.id.tvPrimaryMetricUnit, 9);
        sViewsWithIds.put(R.id.tvPrimaryMetricStatus, 10);
        sViewsWithIds.put(R.id.metricRow, 11);
        sViewsWithIds.put(R.id.cardSteps, 12);
        sViewsWithIds.put(R.id.tvStepsValue, 13);
        sViewsWithIds.put(R.id.progressSteps, 14);
        sViewsWithIds.put(R.id.cardSleep, 15);
        sViewsWithIds.put(R.id.tvSleepValue, 16);
        sViewsWithIds.put(R.id.tvSleepScore, 17);
        sViewsWithIds.put(R.id.cardHydration, 18);
        sViewsWithIds.put(R.id.tvHydrationValue, 19);
        sViewsWithIds.put(R.id.recentLogsSection, 20);
        sViewsWithIds.put(R.id.tvRecentLogsTitle, 21);
        sViewsWithIds.put(R.id.tvViewAll, 22);
        sViewsWithIds.put(R.id.rvRecentLogs, 23);
        sViewsWithIds.put(R.id.emptyState, 24);
        sViewsWithIds.put(R.id.fabAddLog, 25);
        sViewsWithIds.put(R.id.bottomNav, 26);
    }
    // views
    @NonNull
    private final androidx.coordinatorlayout.widget.CoordinatorLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDashboardBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 27, sIncludes, sViewsWithIds));
    }
    private ActivityDashboardBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.google.android.material.bottomnavigation.BottomNavigationView) bindings[26]
            , (com.google.android.material.card.MaterialCardView) bindings[18]
            , (com.google.android.material.card.MaterialCardView) bindings[6]
            , (com.google.android.material.card.MaterialCardView) bindings[4]
            , (com.google.android.material.card.MaterialCardView) bindings[15]
            , (com.google.android.material.card.MaterialCardView) bindings[12]
            , (android.widget.LinearLayout) bindings[24]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[25]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[1]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.ProgressBar) bindings[14]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[20]
            , (androidx.recyclerview.widget.RecyclerView) bindings[23]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[22]
            );
        this.mboundView0 = (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}