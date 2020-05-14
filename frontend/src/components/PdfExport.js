import React from 'react';
import {
  Page,
  Text,
  View,
  Document,
  StyleSheet,
  PDFViewer,
  PDFDownloadLink
} from '@react-pdf/renderer';

const styles = StyleSheet.create({
  page: {
    flexDirection: 'row',
    backgroundColor: '#E4E4E4'
  },
  section: {
    margin: 10,
    padding: 10,
    flexGrow: 1
  }
});

class PdfExport extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      carId: props.carId
    }
  }

  componentDidMount() {
    
  }

  doc() {
    return (
      <Document
        title={this.state.carId}
        author="CarLog">
        <Page size="A4" style={styles.page}>
          <View style={styles.section}>
            <Text>Section #1</Text>
          </View>
          <View style={styles.section}>
            <Text>Section #2</Text>
          </View>
        </Page>
      </Document>
    )
  }

  downloadLink() {
    return (
      <PDFDownloadLink document={this.doc()} fileName="somename.pdf">
        {({ blob, url, loading, error }) => (loading ? 'Loading document...' : 'Download now!')}
      </PDFDownloadLink>
    )
  }

  render() {
    return (
      <PDFViewer width="100%" height="1000">
        {this.doc()}
      </PDFViewer>
    )
  }
}

export default PdfExport