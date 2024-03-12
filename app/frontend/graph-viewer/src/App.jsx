import './App.css';
import { GraphViewer } from './containers/graph-viewer';
import { QueryContainer } from './containers/query-container';
import { AppContextProvider } from './context/app-context';

export default function App() {
    return (
        <AppContextProvider>
            <GraphViewer />
            <QueryContainer />
        </AppContextProvider>

    )
};