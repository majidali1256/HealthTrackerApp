package com.healthtracker.app;

import com.healthtracker.app.di.DatabaseModule;
import com.healthtracker.app.di.FirebaseModule;
import com.healthtracker.app.services.EmergencySOSService_GeneratedInjector;
import com.healthtracker.app.ui.auth.AuthViewModel_HiltModules;
import com.healthtracker.app.ui.auth.LoginActivity_GeneratedInjector;
import com.healthtracker.app.ui.auth.SignUpActivity_GeneratedInjector;
import com.healthtracker.app.ui.common.QuickLogBottomSheet_GeneratedInjector;
import com.healthtracker.app.ui.dashboard.DashboardActivity_GeneratedInjector;
import com.healthtracker.app.ui.dashboard.DashboardViewModel_HiltModules;
import com.healthtracker.app.ui.documents.DocumentVaultActivity_GeneratedInjector;
import com.healthtracker.app.ui.documents.DocumentVaultViewModel_HiltModules;
import com.healthtracker.app.ui.food.FoodLoggerActivity_GeneratedInjector;
import com.healthtracker.app.ui.food.FoodLoggerViewModel_HiltModules;
import com.healthtracker.app.ui.medications.AddMedicationBottomSheet_GeneratedInjector;
import com.healthtracker.app.ui.medications.MedicationsActivity_GeneratedInjector;
import com.healthtracker.app.ui.medications.MedicationsViewModel_HiltModules;
import com.healthtracker.app.ui.profile.MedicalIdActivity_GeneratedInjector;
import com.healthtracker.app.ui.profile.ProfileActivity_GeneratedInjector;
import com.healthtracker.app.ui.profile.ProfileSetupActivity_GeneratedInjector;
import com.healthtracker.app.ui.profile.ProfileSetupViewModel_HiltModules;
import com.healthtracker.app.ui.profile.SettingsActivity_GeneratedInjector;
import com.healthtracker.app.ui.sleep.SleepAnalysisActivity_GeneratedInjector;
import com.healthtracker.app.ui.sleep.SleepAnalysisViewModel_HiltModules;
import com.healthtracker.app.ui.splash.SplashActivity_GeneratedInjector;
import com.healthtracker.app.ui.symptoms.SymptomCheckerActivity_GeneratedInjector;
import com.healthtracker.app.ui.symptoms.SymptomCheckerViewModel_HiltModules;
import com.healthtracker.app.ui.tools.ToolsActivity_GeneratedInjector;
import com.healthtracker.app.ui.trends.TrendsActivity_GeneratedInjector;
import com.healthtracker.app.ui.trends.TrendsViewModel_HiltModules;
import com.healthtracker.app.ui.vitals.LogVitalsActivity_GeneratedInjector;
import com.healthtracker.app.ui.vitals.LogVitalsViewModel_HiltModules;
import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.components.ServiceComponent;
import dagger.hilt.android.components.ViewComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.components.ViewWithFragmentComponent;
import dagger.hilt.android.flags.FragmentGetContextFix;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_DefaultViewModelFactories_ActivityModule;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_HiltViewModelFactory_ViewModelModule;
import dagger.hilt.android.internal.managers.ActivityComponentManager;
import dagger.hilt.android.internal.managers.FragmentComponentManager;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_LifecycleModule;
import dagger.hilt.android.internal.managers.HiltWrapper_SavedStateHandleModule;
import dagger.hilt.android.internal.managers.ServiceComponentManager;
import dagger.hilt.android.internal.managers.ViewComponentManager;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.HiltWrapper_ActivityModule;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import dagger.hilt.android.scopes.ActivityScoped;
import dagger.hilt.android.scopes.FragmentScoped;
import dagger.hilt.android.scopes.ServiceScoped;
import dagger.hilt.android.scopes.ViewModelScoped;
import dagger.hilt.android.scopes.ViewScoped;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedComponent;
import dagger.hilt.migration.DisableInstallInCheck;
import javax.annotation.processing.Generated;
import javax.inject.Singleton;

@Generated("dagger.hilt.processor.internal.root.RootProcessor")
public final class HealthTrackerApp_HiltComponents {
  private HealthTrackerApp_HiltComponents() {
  }

  @Module(
      subcomponents = ServiceC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ServiceCBuilderModule {
    @Binds
    ServiceComponentBuilder bind(ServiceC.Builder builder);
  }

  @Module(
      subcomponents = ActivityRetainedC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ActivityRetainedCBuilderModule {
    @Binds
    ActivityRetainedComponentBuilder bind(ActivityRetainedC.Builder builder);
  }

  @Module(
      subcomponents = ActivityC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ActivityCBuilderModule {
    @Binds
    ActivityComponentBuilder bind(ActivityC.Builder builder);
  }

  @Module(
      subcomponents = ViewModelC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewModelCBuilderModule {
    @Binds
    ViewModelComponentBuilder bind(ViewModelC.Builder builder);
  }

  @Module(
      subcomponents = ViewC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewCBuilderModule {
    @Binds
    ViewComponentBuilder bind(ViewC.Builder builder);
  }

  @Module(
      subcomponents = FragmentC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface FragmentCBuilderModule {
    @Binds
    FragmentComponentBuilder bind(FragmentC.Builder builder);
  }

  @Module(
      subcomponents = ViewWithFragmentC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewWithFragmentCBuilderModule {
    @Binds
    ViewWithFragmentComponentBuilder bind(ViewWithFragmentC.Builder builder);
  }

  @Component(
      modules = {
          ApplicationContextModule.class,
          DatabaseModule.class,
          FirebaseModule.class,
          ActivityRetainedCBuilderModule.class,
          ServiceCBuilderModule.class,
          HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule.class
      }
  )
  @Singleton
  public abstract static class SingletonC implements HealthTrackerApp_GeneratedInjector,
      FragmentGetContextFix.FragmentGetContextFixEntryPoint,
      HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint,
      ServiceComponentManager.ServiceComponentBuilderEntryPoint,
      SingletonComponent,
      GeneratedComponent {
  }

  @Subcomponent
  @ServiceScoped
  public abstract static class ServiceC implements EmergencySOSService_GeneratedInjector,
      ServiceComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ServiceComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          AuthViewModel_HiltModules.KeyModule.class,
          DashboardViewModel_HiltModules.KeyModule.class,
          DocumentVaultViewModel_HiltModules.KeyModule.class,
          FoodLoggerViewModel_HiltModules.KeyModule.class,
          ActivityCBuilderModule.class,
          ViewModelCBuilderModule.class,
          HiltWrapper_ActivityRetainedComponentManager_LifecycleModule.class,
          HiltWrapper_SavedStateHandleModule.class,
          LogVitalsViewModel_HiltModules.KeyModule.class,
          MedicationsViewModel_HiltModules.KeyModule.class,
          ProfileSetupViewModel_HiltModules.KeyModule.class,
          SleepAnalysisViewModel_HiltModules.KeyModule.class,
          SymptomCheckerViewModel_HiltModules.KeyModule.class,
          TrendsViewModel_HiltModules.KeyModule.class
      }
  )
  @ActivityRetainedScoped
  public abstract static class ActivityRetainedC implements ActivityRetainedComponent,
      ActivityComponentManager.ActivityComponentBuilderEntryPoint,
      HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ActivityRetainedComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          FragmentCBuilderModule.class,
          ViewCBuilderModule.class,
          HiltWrapper_ActivityModule.class,
          HiltWrapper_DefaultViewModelFactories_ActivityModule.class
      }
  )
  @ActivityScoped
  public abstract static class ActivityC implements LoginActivity_GeneratedInjector,
      SignUpActivity_GeneratedInjector,
      DashboardActivity_GeneratedInjector,
      DocumentVaultActivity_GeneratedInjector,
      FoodLoggerActivity_GeneratedInjector,
      MedicationsActivity_GeneratedInjector,
      MedicalIdActivity_GeneratedInjector,
      ProfileActivity_GeneratedInjector,
      ProfileSetupActivity_GeneratedInjector,
      SettingsActivity_GeneratedInjector,
      SleepAnalysisActivity_GeneratedInjector,
      SplashActivity_GeneratedInjector,
      SymptomCheckerActivity_GeneratedInjector,
      ToolsActivity_GeneratedInjector,
      TrendsActivity_GeneratedInjector,
      LogVitalsActivity_GeneratedInjector,
      ActivityComponent,
      DefaultViewModelFactories.ActivityEntryPoint,
      HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint,
      FragmentComponentManager.FragmentComponentBuilderEntryPoint,
      ViewComponentManager.ViewComponentBuilderEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ActivityComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          AuthViewModel_HiltModules.BindsModule.class,
          DashboardViewModel_HiltModules.BindsModule.class,
          DocumentVaultViewModel_HiltModules.BindsModule.class,
          FoodLoggerViewModel_HiltModules.BindsModule.class,
          HiltWrapper_HiltViewModelFactory_ViewModelModule.class,
          LogVitalsViewModel_HiltModules.BindsModule.class,
          MedicationsViewModel_HiltModules.BindsModule.class,
          ProfileSetupViewModel_HiltModules.BindsModule.class,
          SleepAnalysisViewModel_HiltModules.BindsModule.class,
          SymptomCheckerViewModel_HiltModules.BindsModule.class,
          TrendsViewModel_HiltModules.BindsModule.class
      }
  )
  @ViewModelScoped
  public abstract static class ViewModelC implements ViewModelComponent,
      HiltViewModelFactory.ViewModelFactoriesEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewModelComponentBuilder {
    }
  }

  @Subcomponent
  @ViewScoped
  public abstract static class ViewC implements ViewComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewComponentBuilder {
    }
  }

  @Subcomponent(
      modules = ViewWithFragmentCBuilderModule.class
  )
  @FragmentScoped
  public abstract static class FragmentC implements QuickLogBottomSheet_GeneratedInjector,
      AddMedicationBottomSheet_GeneratedInjector,
      FragmentComponent,
      DefaultViewModelFactories.FragmentEntryPoint,
      ViewComponentManager.ViewWithFragmentComponentBuilderEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends FragmentComponentBuilder {
    }
  }

  @Subcomponent
  @ViewScoped
  public abstract static class ViewWithFragmentC implements ViewWithFragmentComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewWithFragmentComponentBuilder {
    }
  }
}
