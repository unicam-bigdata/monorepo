import './App.css';
import { GraphViewer } from './containers/graph-viewer';
import { SidePanel } from './containers/side-panel-container';
import { QueryContainer } from './containers/query-container';
import { AppContextProvider } from './context/app-context';

export default function App() {
    return (
        <AppContextProvider>
            <GraphViewer />
            <SidePanel />
            <QueryContainer />
        </AppContextProvider>
    )
};