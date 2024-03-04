import './App.css';
import { ExpandableGraph } from './expandable-graph.jsx';
import { genRandomTree } from './graph.js';

export default function App() {
return <ExpandableGraph graphData={genRandomTree()}/>
};