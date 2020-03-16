import {KeyValuePipe} from './key-value.pipe';

describe('KeyValuePipe', () => {
  const value = {
    test1: 'a',
    test2: 'b',
    test3: 'c',
    test4: 'd'
  };

  it('create an instance', () => {
    const pipe = new KeyValuePipe();
    expect(pipe).toBeTruthy();
  });

  it('transform', () => {
    const pipe = new KeyValuePipe();
    expect(pipe.transform(value)).toEqual([
      {key: 'test1', value: 'a'},
      {key: 'test2', value: 'b'},
      {key: 'test3', value: 'c'},
      {key: 'test4', value: 'd'},
    ]);
  });

  it('transform empty object', () => {
    const pipe = new KeyValuePipe();
    expect(pipe.transform({})).toEqual([]);
  });

  it('transform', () => {
    const pipe = new KeyValuePipe();
    expect(pipe.transform(undefined)).toBe(undefined);
  });
});
