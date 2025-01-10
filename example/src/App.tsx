import { useEffect, useState } from 'react';
import { Text, View, StyleSheet } from 'react-native';
import {
  checkBootloaderUnlocked,
  checkRootJail,
} from 'react-native-root-checker';

export default function App() {
  const [bootLoaderStatus, setBootLoaderStatus] = useState(false);
  const [rootJailStatus, setRootJailStatus] = useState(false);
  useEffect(() => {
    setBootLoaderStatus(checkBootloaderUnlocked());
    setRootJailStatus(checkRootJail());
  }, []);
  return (
    <View style={styles.container}>
      <Text>{`BootLoader Status: ${bootLoaderStatus}`}</Text>
      <Text>{`RootJail Status: ${rootJailStatus}`}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
