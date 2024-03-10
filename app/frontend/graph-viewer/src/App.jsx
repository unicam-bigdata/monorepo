import './App.css';
import { GraphViewer } from './containers/graph-viewer';
import { AppContextProvider } from './context/app-context';

export default function App() {
    return (
        <AppContextProvider>
            <GraphViewer />
        </AppContextProvider>

    )
};