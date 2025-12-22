package com.healthtracker.app;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.healthtracker.app.data.local.AppDatabase;
import com.healthtracker.app.data.local.dao.ActivityRecordDao;
import com.healthtracker.app.data.local.dao.DocumentDao;
import com.healthtracker.app.data.local.dao.HealthLogDao;
import com.healthtracker.app.data.local.dao.MedicationDao;
import com.healthtracker.app.data.local.dao.SleepRecordDao;
import com.healthtracker.app.data.local.dao.UserDao;
import com.healthtracker.app.data.local.dao.VitalRecordDao;
import com.healthtracker.app.data.remote.FirebaseService;
import com.healthtracker.app.data.repository.AuthRepository;
import com.healthtracker.app.data.repository.HealthRepository;
import com.healthtracker.app.di.DatabaseModule_ProvideActivityRecordDaoFactory;
import com.healthtracker.app.di.DatabaseModule_ProvideAppDatabaseFactory;
import com.healthtracker.app.di.DatabaseModule_ProvideDocumentDaoFactory;
import com.healthtracker.app.di.DatabaseModule_ProvideHealthLogDaoFactory;
import com.healthtracker.app.di.DatabaseModule_ProvideMedicationDaoFactory;
import com.healthtracker.app.di.DatabaseModule_ProvideSleepRecordDaoFactory;
import com.healthtracker.app.di.DatabaseModule_ProvideUserDaoFactory;
import com.healthtracker.app.di.DatabaseModule_ProvideVitalRecordDaoFactory;
import com.healthtracker.app.di.FirebaseModule_ProvideFirebaseAuthFactory;
import com.healthtracker.app.di.FirebaseModule_ProvideFirebaseFirestoreFactory;
import com.healthtracker.app.di.FirebaseModule_ProvideFirebaseStorageFactory;
import com.healthtracker.app.di.FirebaseModule_ProvideGoogleSignInClientFactory;
import com.healthtracker.app.di.FirebaseModule_ProvideGoogleSignInOptionsFactory;
import com.healthtracker.app.services.EmergencySOSService;
import com.healthtracker.app.services.EmergencySOSService_MembersInjector;
import com.healthtracker.app.services.StepCounterService;
import com.healthtracker.app.services.StepCounterService_MembersInjector;
import com.healthtracker.app.ui.auth.AuthViewModel;
import com.healthtracker.app.ui.auth.AuthViewModel_HiltModules;
import com.healthtracker.app.ui.auth.LoginActivity;
import com.healthtracker.app.ui.auth.LoginActivity_MembersInjector;
import com.healthtracker.app.ui.auth.SignUpActivity;
import com.healthtracker.app.ui.auth.SignUpActivity_MembersInjector;
import com.healthtracker.app.ui.dashboard.DashboardActivity;
import com.healthtracker.app.ui.dashboard.DashboardActivity_MembersInjector;
import com.healthtracker.app.ui.dashboard.DashboardViewModel;
import com.healthtracker.app.ui.dashboard.DashboardViewModel_HiltModules;
import com.healthtracker.app.ui.documents.DocumentVaultActivity;
import com.healthtracker.app.ui.documents.DocumentVaultViewModel;
import com.healthtracker.app.ui.documents.DocumentVaultViewModel_HiltModules;
import com.healthtracker.app.ui.food.FoodLoggerActivity;
import com.healthtracker.app.ui.food.FoodLoggerActivity_MembersInjector;
import com.healthtracker.app.ui.food.FoodLoggerViewModel;
import com.healthtracker.app.ui.food.FoodLoggerViewModel_HiltModules;
import com.healthtracker.app.ui.medications.MedicationsActivity;
import com.healthtracker.app.ui.medications.MedicationsActivity_MembersInjector;
import com.healthtracker.app.ui.medications.MedicationsViewModel;
import com.healthtracker.app.ui.medications.MedicationsViewModel_HiltModules;
import com.healthtracker.app.ui.profile.MedicalIdActivity;
import com.healthtracker.app.ui.profile.ProfileActivity;
import com.healthtracker.app.ui.profile.ProfileSetupActivity;
import com.healthtracker.app.ui.profile.ProfileSetupActivity_MembersInjector;
import com.healthtracker.app.ui.profile.ProfileSetupViewModel;
import com.healthtracker.app.ui.profile.ProfileSetupViewModel_HiltModules;
import com.healthtracker.app.ui.profile.SettingsActivity;
import com.healthtracker.app.ui.sleep.SleepAnalysisActivity;
import com.healthtracker.app.ui.sleep.SleepAnalysisActivity_MembersInjector;
import com.healthtracker.app.ui.sleep.SleepAnalysisViewModel;
import com.healthtracker.app.ui.sleep.SleepAnalysisViewModel_HiltModules;
import com.healthtracker.app.ui.splash.SplashActivity;
import com.healthtracker.app.ui.splash.SplashActivity_MembersInjector;
import com.healthtracker.app.ui.symptoms.SymptomCheckerActivity;
import com.healthtracker.app.ui.symptoms.SymptomCheckerActivity_MembersInjector;
import com.healthtracker.app.ui.symptoms.SymptomCheckerViewModel;
import com.healthtracker.app.ui.symptoms.SymptomCheckerViewModel_HiltModules;
import com.healthtracker.app.ui.tools.ToolsActivity;
import com.healthtracker.app.ui.tools.ToolsActivity_MembersInjector;
import com.healthtracker.app.ui.trends.TrendsActivity;
import com.healthtracker.app.ui.trends.TrendsActivity_MembersInjector;
import com.healthtracker.app.ui.trends.TrendsViewModel;
import com.healthtracker.app.ui.trends.TrendsViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class DaggerHealthTrackerApp_HiltComponents_SingletonC {
  private DaggerHealthTrackerApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public HealthTrackerApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements HealthTrackerApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public HealthTrackerApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements HealthTrackerApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public HealthTrackerApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements HealthTrackerApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public HealthTrackerApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements HealthTrackerApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public HealthTrackerApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements HealthTrackerApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public HealthTrackerApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements HealthTrackerApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public HealthTrackerApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements HealthTrackerApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public HealthTrackerApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends HealthTrackerApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends HealthTrackerApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends HealthTrackerApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends HealthTrackerApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectLoginActivity(LoginActivity loginActivity) {
      injectLoginActivity2(loginActivity);
    }

    @Override
    public void injectSignUpActivity(SignUpActivity signUpActivity) {
      injectSignUpActivity2(signUpActivity);
    }

    @Override
    public void injectDashboardActivity(DashboardActivity dashboardActivity) {
      injectDashboardActivity2(dashboardActivity);
    }

    @Override
    public void injectDocumentVaultActivity(DocumentVaultActivity documentVaultActivity) {
    }

    @Override
    public void injectFoodLoggerActivity(FoodLoggerActivity foodLoggerActivity) {
      injectFoodLoggerActivity2(foodLoggerActivity);
    }

    @Override
    public void injectMedicationsActivity(MedicationsActivity medicationsActivity) {
      injectMedicationsActivity2(medicationsActivity);
    }

    @Override
    public void injectMedicalIdActivity(MedicalIdActivity medicalIdActivity) {
    }

    @Override
    public void injectProfileActivity(ProfileActivity profileActivity) {
    }

    @Override
    public void injectProfileSetupActivity(ProfileSetupActivity profileSetupActivity) {
      injectProfileSetupActivity2(profileSetupActivity);
    }

    @Override
    public void injectSettingsActivity(SettingsActivity settingsActivity) {
    }

    @Override
    public void injectSleepAnalysisActivity(SleepAnalysisActivity sleepAnalysisActivity) {
      injectSleepAnalysisActivity2(sleepAnalysisActivity);
    }

    @Override
    public void injectSplashActivity(SplashActivity splashActivity) {
      injectSplashActivity2(splashActivity);
    }

    @Override
    public void injectSymptomCheckerActivity(SymptomCheckerActivity symptomCheckerActivity) {
      injectSymptomCheckerActivity2(symptomCheckerActivity);
    }

    @Override
    public void injectToolsActivity(ToolsActivity toolsActivity) {
      injectToolsActivity2(toolsActivity);
    }

    @Override
    public void injectTrendsActivity(TrendsActivity trendsActivity) {
      injectTrendsActivity2(trendsActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(ImmutableMap.<String, Boolean>builderWithExpectedSize(9).put(LazyClassKeyProvider.com_healthtracker_app_ui_auth_AuthViewModel, AuthViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_healthtracker_app_ui_dashboard_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_healthtracker_app_ui_documents_DocumentVaultViewModel, DocumentVaultViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_healthtracker_app_ui_food_FoodLoggerViewModel, FoodLoggerViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_healthtracker_app_ui_medications_MedicationsViewModel, MedicationsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_healthtracker_app_ui_profile_ProfileSetupViewModel, ProfileSetupViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_healthtracker_app_ui_sleep_SleepAnalysisViewModel, SleepAnalysisViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_healthtracker_app_ui_symptoms_SymptomCheckerViewModel, SymptomCheckerViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_healthtracker_app_ui_trends_TrendsViewModel, TrendsViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @CanIgnoreReturnValue
    private LoginActivity injectLoginActivity2(LoginActivity instance) {
      LoginActivity_MembersInjector.injectGoogleSignInClient(instance, singletonCImpl.provideGoogleSignInClientProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private SignUpActivity injectSignUpActivity2(SignUpActivity instance2) {
      SignUpActivity_MembersInjector.injectGoogleSignInClient(instance2, singletonCImpl.provideGoogleSignInClientProvider.get());
      return instance2;
    }

    @CanIgnoreReturnValue
    private DashboardActivity injectDashboardActivity2(DashboardActivity instance3) {
      DashboardActivity_MembersInjector.injectFirebaseAuth(instance3, singletonCImpl.provideFirebaseAuthProvider.get());
      return instance3;
    }

    @CanIgnoreReturnValue
    private FoodLoggerActivity injectFoodLoggerActivity2(FoodLoggerActivity instance4) {
      FoodLoggerActivity_MembersInjector.injectFirebaseAuth(instance4, singletonCImpl.provideFirebaseAuthProvider.get());
      return instance4;
    }

    @CanIgnoreReturnValue
    private MedicationsActivity injectMedicationsActivity2(MedicationsActivity instance5) {
      MedicationsActivity_MembersInjector.injectFirebaseAuth(instance5, singletonCImpl.provideFirebaseAuthProvider.get());
      return instance5;
    }

    @CanIgnoreReturnValue
    private ProfileSetupActivity injectProfileSetupActivity2(ProfileSetupActivity instance6) {
      ProfileSetupActivity_MembersInjector.injectFirebaseAuth(instance6, singletonCImpl.provideFirebaseAuthProvider.get());
      ProfileSetupActivity_MembersInjector.injectUserDao(instance6, singletonCImpl.userDao());
      return instance6;
    }

    @CanIgnoreReturnValue
    private SleepAnalysisActivity injectSleepAnalysisActivity2(SleepAnalysisActivity instance7) {
      SleepAnalysisActivity_MembersInjector.injectFirebaseAuth(instance7, singletonCImpl.provideFirebaseAuthProvider.get());
      return instance7;
    }

    @CanIgnoreReturnValue
    private SplashActivity injectSplashActivity2(SplashActivity instance8) {
      SplashActivity_MembersInjector.injectFirebaseAuth(instance8, singletonCImpl.provideFirebaseAuthProvider.get());
      return instance8;
    }

    @CanIgnoreReturnValue
    private SymptomCheckerActivity injectSymptomCheckerActivity2(SymptomCheckerActivity instance9) {
      SymptomCheckerActivity_MembersInjector.injectFirebaseAuth(instance9, singletonCImpl.provideFirebaseAuthProvider.get());
      return instance9;
    }

    @CanIgnoreReturnValue
    private ToolsActivity injectToolsActivity2(ToolsActivity instance10) {
      ToolsActivity_MembersInjector.injectFirebaseAuth(instance10, singletonCImpl.provideFirebaseAuthProvider.get());
      return instance10;
    }

    @CanIgnoreReturnValue
    private TrendsActivity injectTrendsActivity2(TrendsActivity instance11) {
      TrendsActivity_MembersInjector.injectFirebaseAuth(instance11, singletonCImpl.provideFirebaseAuthProvider.get());
      return instance11;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_healthtracker_app_ui_auth_AuthViewModel = "com.healthtracker.app.ui.auth.AuthViewModel";

      static String com_healthtracker_app_ui_food_FoodLoggerViewModel = "com.healthtracker.app.ui.food.FoodLoggerViewModel";

      static String com_healthtracker_app_ui_dashboard_DashboardViewModel = "com.healthtracker.app.ui.dashboard.DashboardViewModel";

      static String com_healthtracker_app_ui_documents_DocumentVaultViewModel = "com.healthtracker.app.ui.documents.DocumentVaultViewModel";

      static String com_healthtracker_app_ui_sleep_SleepAnalysisViewModel = "com.healthtracker.app.ui.sleep.SleepAnalysisViewModel";

      static String com_healthtracker_app_ui_medications_MedicationsViewModel = "com.healthtracker.app.ui.medications.MedicationsViewModel";

      static String com_healthtracker_app_ui_symptoms_SymptomCheckerViewModel = "com.healthtracker.app.ui.symptoms.SymptomCheckerViewModel";

      static String com_healthtracker_app_ui_profile_ProfileSetupViewModel = "com.healthtracker.app.ui.profile.ProfileSetupViewModel";

      static String com_healthtracker_app_ui_trends_TrendsViewModel = "com.healthtracker.app.ui.trends.TrendsViewModel";

      @KeepFieldType
      AuthViewModel com_healthtracker_app_ui_auth_AuthViewModel2;

      @KeepFieldType
      FoodLoggerViewModel com_healthtracker_app_ui_food_FoodLoggerViewModel2;

      @KeepFieldType
      DashboardViewModel com_healthtracker_app_ui_dashboard_DashboardViewModel2;

      @KeepFieldType
      DocumentVaultViewModel com_healthtracker_app_ui_documents_DocumentVaultViewModel2;

      @KeepFieldType
      SleepAnalysisViewModel com_healthtracker_app_ui_sleep_SleepAnalysisViewModel2;

      @KeepFieldType
      MedicationsViewModel com_healthtracker_app_ui_medications_MedicationsViewModel2;

      @KeepFieldType
      SymptomCheckerViewModel com_healthtracker_app_ui_symptoms_SymptomCheckerViewModel2;

      @KeepFieldType
      ProfileSetupViewModel com_healthtracker_app_ui_profile_ProfileSetupViewModel2;

      @KeepFieldType
      TrendsViewModel com_healthtracker_app_ui_trends_TrendsViewModel2;
    }
  }

  private static final class ViewModelCImpl extends HealthTrackerApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<DocumentVaultViewModel> documentVaultViewModelProvider;

    private Provider<FoodLoggerViewModel> foodLoggerViewModelProvider;

    private Provider<MedicationsViewModel> medicationsViewModelProvider;

    private Provider<ProfileSetupViewModel> profileSetupViewModelProvider;

    private Provider<SleepAnalysisViewModel> sleepAnalysisViewModelProvider;

    private Provider<SymptomCheckerViewModel> symptomCheckerViewModelProvider;

    private Provider<TrendsViewModel> trendsViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.documentVaultViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.foodLoggerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.medicationsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.profileSetupViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.sleepAnalysisViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.symptomCheckerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.trendsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(9).put(LazyClassKeyProvider.com_healthtracker_app_ui_auth_AuthViewModel, ((Provider) authViewModelProvider)).put(LazyClassKeyProvider.com_healthtracker_app_ui_dashboard_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.com_healthtracker_app_ui_documents_DocumentVaultViewModel, ((Provider) documentVaultViewModelProvider)).put(LazyClassKeyProvider.com_healthtracker_app_ui_food_FoodLoggerViewModel, ((Provider) foodLoggerViewModelProvider)).put(LazyClassKeyProvider.com_healthtracker_app_ui_medications_MedicationsViewModel, ((Provider) medicationsViewModelProvider)).put(LazyClassKeyProvider.com_healthtracker_app_ui_profile_ProfileSetupViewModel, ((Provider) profileSetupViewModelProvider)).put(LazyClassKeyProvider.com_healthtracker_app_ui_sleep_SleepAnalysisViewModel, ((Provider) sleepAnalysisViewModelProvider)).put(LazyClassKeyProvider.com_healthtracker_app_ui_symptoms_SymptomCheckerViewModel, ((Provider) symptomCheckerViewModelProvider)).put(LazyClassKeyProvider.com_healthtracker_app_ui_trends_TrendsViewModel, ((Provider) trendsViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_healthtracker_app_ui_documents_DocumentVaultViewModel = "com.healthtracker.app.ui.documents.DocumentVaultViewModel";

      static String com_healthtracker_app_ui_sleep_SleepAnalysisViewModel = "com.healthtracker.app.ui.sleep.SleepAnalysisViewModel";

      static String com_healthtracker_app_ui_symptoms_SymptomCheckerViewModel = "com.healthtracker.app.ui.symptoms.SymptomCheckerViewModel";

      static String com_healthtracker_app_ui_auth_AuthViewModel = "com.healthtracker.app.ui.auth.AuthViewModel";

      static String com_healthtracker_app_ui_medications_MedicationsViewModel = "com.healthtracker.app.ui.medications.MedicationsViewModel";

      static String com_healthtracker_app_ui_food_FoodLoggerViewModel = "com.healthtracker.app.ui.food.FoodLoggerViewModel";

      static String com_healthtracker_app_ui_profile_ProfileSetupViewModel = "com.healthtracker.app.ui.profile.ProfileSetupViewModel";

      static String com_healthtracker_app_ui_trends_TrendsViewModel = "com.healthtracker.app.ui.trends.TrendsViewModel";

      static String com_healthtracker_app_ui_dashboard_DashboardViewModel = "com.healthtracker.app.ui.dashboard.DashboardViewModel";

      @KeepFieldType
      DocumentVaultViewModel com_healthtracker_app_ui_documents_DocumentVaultViewModel2;

      @KeepFieldType
      SleepAnalysisViewModel com_healthtracker_app_ui_sleep_SleepAnalysisViewModel2;

      @KeepFieldType
      SymptomCheckerViewModel com_healthtracker_app_ui_symptoms_SymptomCheckerViewModel2;

      @KeepFieldType
      AuthViewModel com_healthtracker_app_ui_auth_AuthViewModel2;

      @KeepFieldType
      MedicationsViewModel com_healthtracker_app_ui_medications_MedicationsViewModel2;

      @KeepFieldType
      FoodLoggerViewModel com_healthtracker_app_ui_food_FoodLoggerViewModel2;

      @KeepFieldType
      ProfileSetupViewModel com_healthtracker_app_ui_profile_ProfileSetupViewModel2;

      @KeepFieldType
      TrendsViewModel com_healthtracker_app_ui_trends_TrendsViewModel2;

      @KeepFieldType
      DashboardViewModel com_healthtracker_app_ui_dashboard_DashboardViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.healthtracker.app.ui.auth.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.authRepositoryProvider.get());

          case 1: // com.healthtracker.app.ui.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.healthRepositoryProvider.get());

          case 2: // com.healthtracker.app.ui.documents.DocumentVaultViewModel 
          return (T) new DocumentVaultViewModel(singletonCImpl.healthRepositoryProvider.get());

          case 3: // com.healthtracker.app.ui.food.FoodLoggerViewModel 
          return (T) new FoodLoggerViewModel(singletonCImpl.healthRepositoryProvider.get(), singletonCImpl.provideFirebaseAuthProvider.get());

          case 4: // com.healthtracker.app.ui.medications.MedicationsViewModel 
          return (T) new MedicationsViewModel(singletonCImpl.healthRepositoryProvider.get());

          case 5: // com.healthtracker.app.ui.profile.ProfileSetupViewModel 
          return (T) new ProfileSetupViewModel(singletonCImpl.healthRepositoryProvider.get());

          case 6: // com.healthtracker.app.ui.sleep.SleepAnalysisViewModel 
          return (T) new SleepAnalysisViewModel(singletonCImpl.healthRepositoryProvider.get());

          case 7: // com.healthtracker.app.ui.symptoms.SymptomCheckerViewModel 
          return (T) new SymptomCheckerViewModel();

          case 8: // com.healthtracker.app.ui.trends.TrendsViewModel 
          return (T) new TrendsViewModel(singletonCImpl.healthRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends HealthTrackerApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends HealthTrackerApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectEmergencySOSService(EmergencySOSService emergencySOSService) {
      injectEmergencySOSService2(emergencySOSService);
    }

    @Override
    public void injectStepCounterService(StepCounterService stepCounterService) {
      injectStepCounterService2(stepCounterService);
    }

    @CanIgnoreReturnValue
    private EmergencySOSService injectEmergencySOSService2(EmergencySOSService instance) {
      EmergencySOSService_MembersInjector.injectUserDao(instance, singletonCImpl.userDao());
      EmergencySOSService_MembersInjector.injectFirebaseAuth(instance, singletonCImpl.provideFirebaseAuthProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private StepCounterService injectStepCounterService2(StepCounterService instance2) {
      StepCounterService_MembersInjector.injectActivityRecordDao(instance2, singletonCImpl.activityRecordDao());
      StepCounterService_MembersInjector.injectFirebaseAuth(instance2, singletonCImpl.provideFirebaseAuthProvider.get());
      return instance2;
    }
  }

  private static final class SingletonCImpl extends HealthTrackerApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<GoogleSignInOptions> provideGoogleSignInOptionsProvider;

    private Provider<GoogleSignInClient> provideGoogleSignInClientProvider;

    private Provider<FirebaseAuth> provideFirebaseAuthProvider;

    private Provider<AppDatabase> provideAppDatabaseProvider;

    private Provider<FirebaseFirestore> provideFirebaseFirestoreProvider;

    private Provider<FirebaseStorage> provideFirebaseStorageProvider;

    private Provider<FirebaseService> firebaseServiceProvider;

    private Provider<AuthRepository> authRepositoryProvider;

    private Provider<HealthRepository> healthRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private UserDao userDao() {
      return DatabaseModule_ProvideUserDaoFactory.provideUserDao(provideAppDatabaseProvider.get());
    }

    private HealthLogDao healthLogDao() {
      return DatabaseModule_ProvideHealthLogDaoFactory.provideHealthLogDao(provideAppDatabaseProvider.get());
    }

    private VitalRecordDao vitalRecordDao() {
      return DatabaseModule_ProvideVitalRecordDaoFactory.provideVitalRecordDao(provideAppDatabaseProvider.get());
    }

    private SleepRecordDao sleepRecordDao() {
      return DatabaseModule_ProvideSleepRecordDaoFactory.provideSleepRecordDao(provideAppDatabaseProvider.get());
    }

    private ActivityRecordDao activityRecordDao() {
      return DatabaseModule_ProvideActivityRecordDaoFactory.provideActivityRecordDao(provideAppDatabaseProvider.get());
    }

    private MedicationDao medicationDao() {
      return DatabaseModule_ProvideMedicationDaoFactory.provideMedicationDao(provideAppDatabaseProvider.get());
    }

    private DocumentDao documentDao() {
      return DatabaseModule_ProvideDocumentDaoFactory.provideDocumentDao(provideAppDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideGoogleSignInOptionsProvider = DoubleCheck.provider(new SwitchingProvider<GoogleSignInOptions>(singletonCImpl, 1));
      this.provideGoogleSignInClientProvider = DoubleCheck.provider(new SwitchingProvider<GoogleSignInClient>(singletonCImpl, 0));
      this.provideFirebaseAuthProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseAuth>(singletonCImpl, 2));
      this.provideAppDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 3));
      this.provideFirebaseFirestoreProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseFirestore>(singletonCImpl, 6));
      this.provideFirebaseStorageProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseStorage>(singletonCImpl, 7));
      this.firebaseServiceProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseService>(singletonCImpl, 5));
      this.authRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 4));
      this.healthRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<HealthRepository>(singletonCImpl, 8));
    }

    @Override
    public void injectHealthTrackerApp(HealthTrackerApp healthTrackerApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.google.android.gms.auth.api.signin.GoogleSignInClient 
          return (T) FirebaseModule_ProvideGoogleSignInClientFactory.provideGoogleSignInClient(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideGoogleSignInOptionsProvider.get());

          case 1: // com.google.android.gms.auth.api.signin.GoogleSignInOptions 
          return (T) FirebaseModule_ProvideGoogleSignInOptionsFactory.provideGoogleSignInOptions(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.google.firebase.auth.FirebaseAuth 
          return (T) FirebaseModule_ProvideFirebaseAuthFactory.provideFirebaseAuth();

          case 3: // com.healthtracker.app.data.local.AppDatabase 
          return (T) DatabaseModule_ProvideAppDatabaseFactory.provideAppDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.healthtracker.app.data.repository.AuthRepository 
          return (T) new AuthRepository(singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.userDao(), singletonCImpl.firebaseServiceProvider.get());

          case 5: // com.healthtracker.app.data.remote.FirebaseService 
          return (T) new FirebaseService(singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.provideFirebaseFirestoreProvider.get(), singletonCImpl.provideFirebaseStorageProvider.get());

          case 6: // com.google.firebase.firestore.FirebaseFirestore 
          return (T) FirebaseModule_ProvideFirebaseFirestoreFactory.provideFirebaseFirestore();

          case 7: // com.google.firebase.storage.FirebaseStorage 
          return (T) FirebaseModule_ProvideFirebaseStorageFactory.provideFirebaseStorage();

          case 8: // com.healthtracker.app.data.repository.HealthRepository 
          return (T) new HealthRepository(singletonCImpl.userDao(), singletonCImpl.healthLogDao(), singletonCImpl.vitalRecordDao(), singletonCImpl.sleepRecordDao(), singletonCImpl.activityRecordDao(), singletonCImpl.medicationDao(), singletonCImpl.documentDao(), singletonCImpl.firebaseServiceProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
