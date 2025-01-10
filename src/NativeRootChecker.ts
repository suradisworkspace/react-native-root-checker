import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  checkRootJail(): boolean;
  checkBootloaderUnlocked(): boolean;
}

export default TurboModuleRegistry.getEnforcing<Spec>('RootChecker');
