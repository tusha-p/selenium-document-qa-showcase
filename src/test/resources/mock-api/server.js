const express = require('express');
const app = express();
app.use(express.json());

// In-memory "database" for demo purposes
let documents = [
  { id: 1, title: 'Test Doc 1', status: 'DRAFT', owner: 'user1' },
  { id: 2, title: 'Confidential Doc', status: 'APPROVED', owner: 'admin' }
];
let users = [
  { username: 'user1', role: 'USER' },
  { username: 'admin', role: 'ADMIN' }
];

// API Endpoints for Document Workflow
app.get('/api/documents', (req, res) => {
  res.json(documents);
});

app.get('/api/documents/:id', (req, res) => {
  const doc = documents.find(d => d.id === parseInt(req.params.id));
  if (!doc) return res.status(404).send('Document not found');
  res.json(doc);
});

app.post('/api/documents/:id/approve', (req, res) => {
  // Simulate changing a document's status - a key workflow!
  const doc = documents.find(d => d.id === parseInt(req.params.id));
  if (!doc) return res.status(404).send('Document not found');
  doc.status = 'APPROVED';
  res.json({ message: 'Document approved', doc });
});

// API Endpoint for Permission Check
app.get('/api/admin/users', (req, res) => {
  // This endpoint should only be accessible to admins
  res.json(users);
});

app.listen(8080, () => console.log('Mock DMS API running on port 8080'));
