import RootChecker from './NativeRootChecker';

export function checkRootJail(): boolean {
  return RootChecker.checkRootJail();
}

export function checkBootloaderUnlocked(): boolean {
  return RootChecker.checkBootloaderUnlocked();
}
